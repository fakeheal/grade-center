import React from "react";

export default function UserFields({ values, setFuncs, errors, isEdit = false }) {
    const { firstName, lastName, email, password, repeatPassword } = values;

    const {setFirstName,setLastName,setEmail,setPassword,setRepeatPassword} = setFuncs;
    return (
        <div>
            <div className="mb-2">
                <label className="fieldset-label" htmlFor="firstName">First Name</label>
                <input type="text" className={`input w-full ${errors?.firstName ? `input-error` : ``}`}
                       placeholder="Enter first name..." name="firstName"
                       value={firstName}
                       onChange={(e) => setFirstName(e.target.value)}
                       id="firstName"/>
                {errors?.firstName &&
                    <p className="text-error text-xs mt-1">{errors.firstName}</p>}
            </div>
            <div className="mb-2">
                <label className="fieldset-label" htmlFor="lastName">Last Name</label>
                <input type="text" className={`input w-full ${errors?.lastName ? `input-error` : ``}`}
                       placeholder="Enter last name..." name="lastName"
                       onChange={(e) => setLastName(e.target.value)}
                       value={lastName}
                       id="lastName"/>
                {errors?.lastName &&
                    <p className="text-error text-xs mt-1">{errors.lastName}</p>}
            </div>
            <div className="mb-2">
                <label className="fieldset-label" htmlFor="email">Email</label>
                <input type="text" className={`input w-full ${errors?.email ? `input-error` : ``}`}
                       placeholder="Enter email..." name="email"
                       value={email}
                       onChange={(e) => setEmail(e.target.value)}
                       id="email"/>
                {errors?.email &&
                    <p className="text-error text-xs mt-1">{errors.email}</p>}
            </div>
            <div className="mb-2">
                <label className="fieldset-label" htmlFor="password">Password</label>
                <input type="password" className={`input w-full ${errors?.password ? `input-error` : ``}`}
                       placeholder="Enter password..." name="password"
                       value={password}
                       onChange={(e) => setPassword(e.target.value)}
                       id="password"/>
                {errors?.password &&
                    <p className="text-error text-xs mt-1">{errors.password}</p>}
            </div>
            <div className="mb-2">
                <label className="fieldset-label" htmlFor="repeatPassword">Password (repeat)</label>
                <input type="password" className={`input w-full ${errors?.repeatPassword ? `input-error` : ``}`}
                       placeholder="Repeat password..."
                       name="repeatPassword"
                       value={repeatPassword}
                       onChange={(e) => setRepeatPassword(e.target.value)}
                       id="repeatPassword"/>
                {errors?.repeatPassword &&
                    <p className="text-error text-xs mt-1">{errors.repeatPassword}</p>}
            </div>
        </div>
    )
}