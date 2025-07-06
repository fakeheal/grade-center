export default function ClassSelect({ classes, onClassChanged }) {
  return (
    <select name="classId" id="classId" className={`mt-0.5 select w-full`} onChange={(e) => onClassChanged(e.target.value)}>
      <option value="">Select Class</option>
      {classes.map(item => (
        <option key={item.id} value={item.id}>
          {`${item.grade}${item.name}`}
        </option>
      ))}
    </select>
  );
}
