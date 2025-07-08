// app/routes/authenticated/parents/index.jsx
import {
    Form,
    Link,
    redirect,
    useLoaderData,
    useNavigation,
    useSearchParams,
    useOutletContext,
} from 'react-router';
import apiConfig from '../../../api.config';
import { Trash2, Edit, Users } from 'lucide-react';
import { useState } from 'react';
import { getJwt } from '../../../utilities/auth';

export async function loader({ request }) {
	const url = new URL(request.url);
	const page = url.searchParams.get('page') || 0;

	const token = getJwt(request.headers.get('cookie'));
	const res = await fetch(
		`${apiConfig.baseUrl}/parents?page=${page}&size=20`,
		{ headers: { Authorization: `Bearer ${token}` } }
	);
	if (!res.ok) throw new Response('Failed to load parents', { status: 500 });

	return await res.json();
}

export async function action({ request }) {
	const formData = await request.formData();
	const id = formData.get('deleteId');

	const token = getJwt(request.headers.get('cookie'));

	const res = await fetch(`${apiConfig.baseUrl}/parents/${id}`, {
		method: 'DELETE',
		headers: { Authorization: `Bearer ${token}` },
	});

	if (!res.ok)
		return { error: 'Delete failed. Maybe parent has grades linked.' };

	return redirect('/parents');
}

export default function ParentsIndex() {
	const { content: parents = [], totalPages = 1 } = useLoaderData();
	const navigation = useNavigation();
	const [searchParams] = useSearchParams();
	const page = Number(searchParams.get('page') || 0);

	/* token is available from layout _or_ from cookie as fallback */
	const { token: ctxToken } = useOutletContext() ?? {};
	const token = ctxToken || getJwt(document?.cookie);

	const [isModalOpen, setIsModalOpen] = useState(false);
	const [selectedParentStudents, setSelectedParentStudents] = useState([]);
	const [isLoading, setIsLoading] = useState(false);

	const handleViewStudents = async (parentId) => {
		setIsModalOpen(true);
		setIsLoading(true);
		try {
			const res = await fetch(
				`${apiConfig.baseUrl}/parents/${parentId}/students`,
				{ headers: { Authorization: `Bearer ${token}` } }
			);
			if (!res.ok) {
				throw new Error('Failed to fetch students');
			}
			const students = await res.json();
			setSelectedParentStudents(students);
		} catch (error) {
			console.error(error);
			// Handle error display to the user
		} finally {
			setIsLoading(false);
		}
	};

	return (
		<div className="p-6">
			<div className="flex justify-between items-center mb-4">
				<h1 className="text-2xl font-bold">Parents</h1>
				<Link to="/parents/create" className="btn btn-primary">
					+ Add Parent
				</Link>
			</div>

			<div className="overflow-x-auto shadow rounded">
				<table className="table w-full">
					<thead>
						<tr className="bg-base-200">
							<th>#</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th className="text-center">Actions</th>
						</tr>
					</thead>
					<tbody>
						{parents.map((p, idx) => (
							<tr key={p.id} className="hover">
								<td>{idx + 1 + page * 20}</td>
								<td>{p.firstName}</td>
								<td>{p.lastName}</td>
								<td className="flex gap-2 justify-center">
									<button
										onClick={() => handleViewStudents(p.id)}
										className="btn btn-sm btn-outline"
										title="View Students"
									>
										<Users size={16} />
									</button>
									<Link
										to={`/parents/${p.id}/edit`}
										className="btn btn-sm btn-outline"
										title="Edit"
									>
										<Edit size={16} />
									</Link>
									<Form method="post" className="inline">
										<input type="hidden" name="deleteId" value={p.id} />
										<button
											type="submit"
											className="btn btn-sm btn-error"
											title="Delete"
											disabled={navigation.state === 'submitting'}
											onClick={(e) => {
												if (
													!window.confirm(
														`Delete ${p.firstName} ${p.lastName}?`
													)
												) {
													e.preventDefault();
												}
											}}
										>
											<Trash2 size={16} />
										</button>
									</Form>
								</td>
							</tr>
						))}
						{parents.length === 0 && (
							<tr>
								<td colSpan={4} className="text-center py-8">
									No parents found.
								</td>
							</tr>
						)}
					</tbody>
				</table>
			</div>

			{totalPages > 1 && (
				<div className="join mt-4 flex justify-center">
					{Array.from({ length: totalPages }).map((_, i) => (
						<Link
							key={i}
							to={`/parents?page=${i}`}
							className={`join-item btn btn-xs ${
								i === page ? 'btn-active' : ''
							}`}
						>
							{i + 1}
						</Link>
					))}
				</div>
			)}

			{isModalOpen && (
				<div className="modal modal-open">
					<div className="modal-box">
						<h3 className="font-bold text-lg">Students</h3>
						{isLoading ? (
							<p>Loading...</p>
						) : selectedParentStudents.length > 0 ? (
							<table className="table w-full">
								<thead>
									<tr>
										<th>Name</th>
										<th>Email</th>
									</tr>
								</thead>
								<tbody>
									{selectedParentStudents.map((student) => (
										<tr key={student.id}>
											<td>
												{student.firstName ?? student.user?.firstName} {student.lastName ?? student.user?.lastName}
											</td>
											<td>{student.email}</td>
										</tr>
									))}
								</tbody>
							</table>
						) : (
							<p>No students found for this parent.</p>
						)}
						<div className="modal-action">
							<button
								onClick={() => setIsModalOpen(false)}
								className="btn"
							>
								Close
							</button>
						</div>
					</div>
				</div>
			)}
		</div>
	);
}
