export default function SchoolYearSelect({ schoolYears, onSchoolYearsChanged }) {
    return (
        <select name="schoolYearId" id="schoolYearId" className={`mt-0.5 select w-full`}
                onChange={(e) => onSchoolYearsChanged(e.target.value)}>
            <option value="">Select School Year & Term</option>
            {schoolYears.map(item => (
                <option key={item.id} value={item.id}>
                    {`${item.year} ${item.term === 1 ? 'First term' : 'Second term'}`}
                </option>
            ))}
        </select>
    );
}