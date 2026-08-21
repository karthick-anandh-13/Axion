export default function Footer() {
  return (
    <footer className="px-6 pb-12 pt-24">
      <div className="mx-auto max-w-7xl border-t border-white/10 pt-10">

        <div className="flex flex-col gap-8 md:flex-row md:justify-between">

          <div>
            <h2 className="text-3xl tracking-[0.25em] text-[#F6E7C8]">
              AXION
            </h2>

            <p className="mt-4 max-w-sm text-white/50 leading-7">
              The intelligent operating system for borrowing,
              lending and wealth creation.
            </p>
          </div>

          <div className="grid grid-cols-2 gap-10 text-sm text-white/60">
            <div>
              <p className="mb-3 text-white">Product</p>
              <ul className="space-y-2">
                <li>Borrow</li>
                <li>Lend</li>
                <li>Portfolio</li>
              </ul>
            </div>

            <div>
              <p className="mb-3 text-white">Company</p>
              <ul className="space-y-2">
                <li>Security</li>
                <li>Privacy</li>
                <li>Support</li>
              </ul>
            </div>
          </div>

        </div>

        <div className="mt-10 flex justify-between border-t border-white/10 pt-6 text-xs text-white/40">
          <p>© 2026 AXION</p>
          <p>Designed for the future of finance.</p>
        </div>

      </div>
    </footer>
  );
}