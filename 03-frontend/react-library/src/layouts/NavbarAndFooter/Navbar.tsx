import { NavLink } from "react-router-dom";
import { useOktaAuth } from "@okta/okta-react";
import { SpinnerLoading } from "../Utils/SpinnerLoading";

export const Navbar = () => {
    const { oktaAuth, authState } = useOktaAuth();

    if (!authState) {
        return <SpinnerLoading />;
    }

    const handleLogin = async () => oktaAuth.signOut();

    console.log(authState);

    return (
        <nav className="navbar navbar-expand-lg navbar-dark main-color py-3">
            <div className='container-fluid'>
                <span className="navbar-brand">Library</span>
                <button className="navbar-toggler" type="button" 
                    data-bs-toggle='collapse' data-bs-target='#navbarNavDropdown'
                    aria-controls="navbaNavDropdown" aria-expanded='false'
                    aria-label="Toggle navigation">
                    <span className='navbar-toggler-icon'></span>
                </button>
                <div className='collapse navbar-collapse' id='navbarNavDropdown'>
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <NavLink className='nav-link' to={"/"}>Home</NavLink>
                        </li>
                        <li className='nav-item'>
                            <NavLink className='nav-link' to={"/search"}>Search Books</NavLink>
                        </li>
                    </ul>
                    <ul className='navbar-nav ms-auto'>
                        <li className='nav-item m-1' >
                            <a href='#' type='button' className='btn btn-outline-light'>Sign in</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}