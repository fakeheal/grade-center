import React, { useState } from 'react';
import { ChevronDown } from 'lucide-react';

export default function ParentSelect({
                                         parents = [],
                                         selectedIds = [],
                                         onSelectionChange,
                                         errors = {},
                                     }) {
    const [open, setOpen] = useState(false);

    const handleCheckboxChange = (parentId) => {
        const newSelection = selectedIds.includes(parentId)
            ? selectedIds.filter((id) => id !== parentId)
            : [...selectedIds, parentId];
        onSelectionChange(newSelection);
    };

    return (
        <div className="relative mb-4">
            <label className="label">
                <span className="label-text font-semibold">Parents (Optional)</span>
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
                        : 'Select parent(s)'}
                </span>
                <ChevronDown
                    size={18}
                    className={`transition-transform ${open ? 'rotate-180' : ''}`}
                />
            </button>

            {/* ▼ checklist panel */}
            {open && (
                <div className="absolute z-20 mt-1 w-full bg-base-100 rounded shadow border max-h-60 overflow-y-auto">
                    {parents.length === 0 ? (
                        <p className="px-3 py-2 text-sm italic opacity-70">
                            No parents found.
                        </p>
                    ) : (
                        parents.map((p) => {
                            /* build a safe displayName */
                            const first =
                                p.firstName ?? p.user?.firstName ?? ''; // try merged, then nested
                            const last =
                                p.lastName ?? p.user?.lastName ?? '';
                            const displayName =
                                first || last
                                    ? `${first} ${last}`.trim()
                                    : p.name
                                    ? p.name
                                    : `User #${p.userId ?? p.id}`;

                            return (
                                <label
                                    key={p.id}
                                    className="flex items-center gap-2 px-3 py-2 hover:bg-base-200"
                                >
                                    <input
                                        type="checkbox"
                                        className="checkbox checkbox-sm checkbox-primary"
                                        checked={selectedIds.includes(p.id)}
                                        onChange={() => handleCheckboxChange(p.id)}
                                    />
                                    <span className="flex-1 text-left">
                                        {displayName}
                                    </span>
                                </label>
                            );
                        })
                    )}
                </div>
            )}

            {/* Hidden inputs for form submission */}
            {selectedIds.map((id) => (
                <input type="hidden" name="parentIds" value={id} key={id} />
            ))}

            {errors?.parentIds && (
                <p className="text-error text-xs mt-1">{errors.parentIds}</p>
            )}
        </div>
    );
}
