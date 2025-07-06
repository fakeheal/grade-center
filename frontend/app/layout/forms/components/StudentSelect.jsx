import React, { useState } from 'react';
import { ChevronDown } from 'lucide-react';

export default function StudentSelect({
                                          students = [],
                                          selectedIds = [],
                                          onSelectionChange,
                                          errors = {},
                                          singleSelect = false,
                                      }) {
    const [open, setOpen] = useState(false);

    const handleCheckboxChange = (studentId) => {
        let newSelection;
        if (singleSelect) {
            newSelection = [studentId];
            setOpen(false); // Close dropdown on single selection
        } else {
            newSelection = selectedIds.includes(studentId)
                ? selectedIds.filter((id) => id !== studentId)
                : [...selectedIds, studentId];
        }
        onSelectionChange(newSelection);
    };

    const getDisplayName = (s) => {
        const first = s.firstName ?? s.user?.firstName ?? '';
        const last = s.lastName ?? s.user?.lastName ?? '';
        return (first || last) ? `${first} ${last}`.trim() : `Student #${s.id}`;
    };

    return (
        <div className="relative mb-4">
            <label className="label">
                <span className="label-text font-semibold">Student</span>
            </label>

            <button
                type="button"
                className="input input-bordered w-full flex items-center justify-between"
                onClick={() => setOpen((o) => !o)}
            >
                <span>
                    {selectedIds.length === 1 && singleSelect
                        ? getDisplayName(students.find(s => s.id === selectedIds[0]))
                        : selectedIds.length
                            ? `${selectedIds.length} selected`
                            : 'Select student(s)'}
                </span>
                <ChevronDown
                    size={18}
                    className={`transition-transform ${open ? 'rotate-180' : ''}`}
                />
            </button>

            {open && (
                <div className="absolute z-20 mt-1 w-full bg-base-100 rounded shadow border max-h-60 overflow-y-auto">
                    {students.length === 0 ? (
                        <p className="px-3 py-2 text-sm italic opacity-70">
                            No students found.
                        </p>
                    ) : (
                        students.map((s) => (
                            <label
                                key={s.id}
                                className="flex items-center gap-2 px-3 py-2 hover:bg-base-200"
                            >
                                <input
                                    type={singleSelect ? "radio" : "checkbox"}
                                    name="studentSelect"
                                    value={s.id}
                                    checked={selectedIds.includes(s.id)}
                                    onChange={() => handleCheckboxChange(s.id)}
                                    className={singleSelect ? "radio radio-sm radio-primary" : "checkbox checkbox-sm checkbox-primary"}
                                />
                                <span className="flex-1 text-left">
                                    {getDisplayName(s)}
                                </span>
                            </label>
                        ))
                    )}
                </div>
            )}

            {selectedIds.map((id) => (
                <input type="hidden" name="studentIds" value={id} key={id} />
            ))}

            {errors?.studentIds && (
                <p className="text-error text-xs mt-1">{errors.studentIds}</p>
            )}
        </div>
    );
}
