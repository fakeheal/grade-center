import React, { useState } from 'react';
import { ChevronDown } from 'lucide-react';

export default function SubjectSelect({
  subjects = [],
  selectedIds = [],
  onSelectionChange,
  errors = {},
  singleSelect = false,
}) {
  const [open, setOpen] = useState(false);

  const handleCheckboxChange = (subjectId) => {
    let newSelection;
    if (singleSelect) {
      newSelection = [subjectId];
      setOpen(false); // Close dropdown after single selection
    } else {
      newSelection = selectedIds.includes(subjectId)
        ? selectedIds.filter((id) => id !== subjectId)
        : [...selectedIds, subjectId];
    }
    onSelectionChange(newSelection);
  };

  const displaySelected = () => {
    if (selectedIds.length === 0) {
      return 'Select subject(s)';
    } else if (singleSelect) {
      const selectedSubject = subjects.find(s => s.id === selectedIds[0]);
      return selectedSubject ? selectedSubject.name : 'Select subject';
    } else {
      return `${selectedIds.length} selected`;
    }
  };

  return (
    <div className="relative mb-4">
      <button
        type="button"
        className={`input input-bordered w-full flex items-center justify-between ${errors?.subjects || errors?.subjectId ? 'input-error' : ''}`}
        onClick={() => setOpen((o) => !o)}
      >
        <span>{displaySelected()}</span>
        <ChevronDown
          size={18}
          className={`transition-transform ${open ? 'rotate-180' : ''}`}
        />
      </button>

      {open && (
        <div className="absolute z-20 mt-1 w-full bg-base-100 rounded shadow border max-h-60 overflow-y-auto">
          {subjects.length === 0 && (
            <p className="px-3 py-2 text-sm italic opacity-70">
              No subjects found.
            </p>
          )}

          {subjects.map((s) => (
            <label
              key={s.id}
              className="flex items-center gap-2 px-3 py-2 hover:bg-base-200"
            >
              <input
                type={singleSelect ? "radio" : "checkbox"}
                className={singleSelect ? "radio radio-primary" : "checkbox checkbox-sm checkbox-primary"}
                checked={singleSelect ? selectedIds[0] === s.id : selectedIds.includes(s.id)}
                onChange={() => handleCheckboxChange(s.id)}
                name={singleSelect ? "subjectId" : ""}
              />
              <span className="flex-1 text-left">
                {s.name}
              </span>
            </label>
          ))}
        </div>
      )}

      {/* Hidden inputs for form submission */}
      {!singleSelect && selectedIds.map((id) => (
        <input type="hidden" name="subjectIds" value={id} key={id} />
      ))}
      {singleSelect && selectedIds[0] && (
        <input type="hidden" name="subjectId" value={selectedIds[0]} />
      )}

      {(errors?.subjects || errors?.subjectId) && (
        <p className="text-error text-xs mt-1">{errors.subjects || errors.subjectId}</p>
      )}
    </div>
  );
}
