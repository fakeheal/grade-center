import React, { useState } from 'react';
import { ChevronDown } from 'lucide-react';

export default function TeacherSelect({
                                          teachers = [],
                                          selectedIds = [],
                                          onSelectionChange,
                                          errors = {},
                                          singleSelect = false,
                                      }) {
    const [open, setOpen] = useState(false);

    const handleCheckboxChange = (teacherId) => {
        let newSelection;
        if (singleSelect) {
            newSelection = [teacherId];
            setOpen(false); // Close dropdown on single selection
        } else {
            newSelection = selectedIds.includes(teacherId)
                ? selectedIds.filter((id) => id !== teacherId)
                : [...selectedIds, teacherId];
        }
        onSelectionChange(newSelection);
    };

    const getDisplayName = (t) => {
        const first = t.firstName ?? t.user?.firstName ?? '';
        const last = t.lastName ?? t.user?.lastName ?? '';
        return (first || last) ? `${first} ${last}`.trim() : `Teacher #${t.id}`;
    };

    return (
        <div className="relative mb-4">
            <label className="label">
                <span className="label-text font-semibold">Teacher</span>
            </label>

            <button
                type="button"
                className="input input-bordered w-full flex items-center justify-between"
                onClick={() => setOpen((o) => !o)}
            >
                <span>
                    {selectedIds.length === 1 && singleSelect
                        ? getDisplayName(teachers.find(t => t.id === selectedIds[0]))
                        : selectedIds.length
                            ? `${selectedIds.length} selected`
                            : 'Select teacher(s)'}
                </span>
                <ChevronDown
                    size={18}
                    className={`transition-transform ${open ? 'rotate-180' : ''}`}
                />
            </button>

            {open && (
                <div className="absolute z-20 mt-1 w-full bg-base-100 rounded shadow border max-h-60 overflow-y-auto">
                    {teachers.length === 0 ? (
                        <p className="px-3 py-2 text-sm italic opacity-70">
                            No teachers found.
                        </p>
                    ) : (
                        teachers.map((t) => (
                            <label
                                key={t.id}
                                className="flex items-center gap-2 px-3 py-2 hover:bg-base-200"
                            >
                                <input
                                    type={singleSelect ? "radio" : "checkbox"}
                                    name="teacherSelect"
                                    value={t.id}
                                    checked={selectedIds.includes(t.id)}
                                    onChange={() => handleCheckboxChange(t.id)}
                                    className={singleSelect ? "radio radio-sm radio-primary" : "checkbox checkbox-sm checkbox-primary"}
                                />
                                <span className="flex-1 text-left">
                                    {getDisplayName(t)}
                                </span>
                            </label>
                        ))
                    )}
                </div>
            )}

            {selectedIds.map((id) => (
                <input type="hidden" name="teacherId" value={id} key={id} />
            ))}

            {errors?.teacherIds && (
                <p className="text-error text-xs mt-1">{errors.teacherIds}</p>
            )}
        </div>
    );
}
