'use client';

export default () => {
  return (
    <footer className="flex justify-around footer p-10 bg-base-300 text-base-content">
      <aside>
        <h3 className="text-xl font-bold">Grade Center</h3>
        <p>Your digital school assistant.</p>
      </aside>
      <nav>
        <header className="footer-title">Links</header>
        <a className="link link-hover">Home</a>
        <a className="link link-hover">Login</a>
        <a className="link link-hover">Sign Up</a>
      </nav>
      <nav>
        <header className="footer-title">Socials</header>
        <a className="link link-hover">LinkedIn</a>
        <a className="link link-hover">Facebook</a>
        <a className="link link-hover">BlueSky</a>
      </nav>
      <nav>
        <header className="footer-title">Help</header>
        <a className="link link-hover">Documentation</a>
        <a className="link link-hover">Contact Us</a>
        <a className="link link-hover">Terms of Use</a>
      </nav>
    </footer>
  );
}
