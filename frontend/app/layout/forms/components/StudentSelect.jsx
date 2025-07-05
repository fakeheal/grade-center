import React, { useState } from 'react';
import { ChevronDown } from 'lucide-react';

/**
 * Dropdown multi-select for linking students to a parent.
 *
 * Props
 * ─────
 * students            array   → [{ id, firstName?, lastName?, userId, user?, grade? }]
 * selectedIds         array   → IDs that should be checked
 * onSelectionChange   function→ Callback when selection changes
 * errors              object  → field-level errors from the action (optional)
 */
export default function StudentSelect({
                                          students = [],
                                          selectedIds = [],
                                          onSelectionChange,
                                          errors = {},
                                      }) {
    const [open, setOpen] = useState(false);

    const handleCheckboxChange = (studentId) => {
        const newSelection = selectedIds.includes(studentId)
            ? selectedIds.filter((id) => id !== studentId)
            : [...selectedIds, studentId];
        onSelectionChange(newSelection);
    };

    return (
        <div className="relative mb-4">
            <label className="label">
                <span className="label-text font-semibold">Children (students)</span>
            </label>

            {/* ▼ “button” */}
            <button
                type="button"
                className="input input-bordered w-full flex items-center justify-between"
                onClick={() => setOpen((o) => !o)}
            >
                <span>
                    {selectedIds.length
                        ? `${selectedIds.length} selected`
                        : 'Select student(s)'}
                </span>
                <ChevronDown
                    size={18}
                    className={`transition-transform ${open ? 'rotate-180' : ''}`}
                />
            </button>

            {/* ▼ checklist panel */}
            {open && (
                <div className="absolute z-20 mt-1 w-full bg-base-100 rounded shadow border max-h-60 overflow-y-auto">
                    {students.length === 0 && (
                        <p className="px-3 py-2 text-sm italic opacity-70">
                            No students found.
                        </p>
                    )}

                    {students.map((s) => {
                        /* build a safe displayName */
                        const first =
                            s.firstName ?? s.user?.firstName ?? ''; // try merged, then nested
                        const last =
                            s.lastName ?? s.user?.lastName ?? '';
                        const displayName =
                            first || last
                                ? `${first} ${last}`.trim()
                                : s.name
                                ? s.name
                                : `User #${s.userId ?? s.id}`;

                        /* choose any grade / class property that exists */
                        const grade =
                            s.grade ??
                            s.gradeLevel ??
                            s.class?.gradeLevel ??
                            s.class?.name ??
                            '';

                        return (
                            <label
                                key={s.id}
                                className="flex items-center gap-2 px-3 py-2 hover:bg-base-200"
                            >
                                <input
                                    type="checkbox"
                                    className="checkbox checkbox-sm checkbox-primary"
                                    checked={selectedIds.includes(s.userId)}
                                    onChange={() => handleCheckboxChange(s.userId)}
                                    disabled={!s.userId}
                                />
                                <span className="flex-1 text-left">
                                    {displayName}
                                    {grade && (
                                        <span className="opacity-60"> — {grade}</span>
                                    )
                                    }
                                </span>
                            </label>
                        );
                    })}
                </div>
            )}

            {/* Hidden inputs for form submission */}
            {selectedIds.map((id) => (
                <input type="hidden" name="studentIds" value={id} key={id} />
            ))}

            {errors?.students && (
                <p className="text-error text-xs mt-1">{errors.students}</p>
            )}
        </div>
    );
}
