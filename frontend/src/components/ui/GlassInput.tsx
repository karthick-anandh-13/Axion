import { useState } from "react";
import { Eye, EyeOff } from "lucide-react";

interface GlassInputProps {
  label: string;
  type?: "text" | "email" | "password" | "number";
  value?: string;
  onChange?: (value: string) => void;
  disabled?: boolean;
}

export default function GlassInput({
  label,
  type = "text",
  value,
  onChange,
  disabled = false,
}: GlassInputProps) {
  const [internalValue, setInternalValue] = useState("");
  const [showPassword, setShowPassword] = useState(false);

  // Works in both controlled and uncontrolled modes
  const inputValue = value ?? internalValue;

  const handleChange = (nextValue: string) => {
    if (value === undefined) {
      setInternalValue(nextValue);
    }
    onChange?.(nextValue);
  };

  const inputType =
    type === "password"
      ? showPassword
        ? "text"
        : "password"
      : type;

  return (
    <div className="relative group">
      <input
        type={inputType}
        value={inputValue}
        onChange={(e) => handleChange(e.target.value)}
        placeholder=" "
        disabled={disabled}
        className="
          peer w-full rounded-2xl
          border border-white/10
          bg-white/5
          px-5 pt-7 pb-3
          text-white
          outline-none
          backdrop-blur-2xl
          transition-all duration-300

          placeholder:text-transparent

          focus:border-[#C7F5D9]
          focus:bg-white/[0.07]
          focus:shadow-[0_0_35px_rgba(199,245,217,0.10)]

          disabled:cursor-not-allowed
          disabled:opacity-50
        "
      />

      {/* Floating Label */}
      <label
        className="
          absolute left-5
          pointer-events-none
          text-white/40
          transition-all duration-300

          top-5 text-base

          peer-focus:top-3
          peer-focus:text-xs
          peer-focus:text-[#C7F5D9]

          peer-not-placeholder-shown:top-3
          peer-not-placeholder-shown:text-xs
        "
      >
        {label}
      </label>

      {/* Password Toggle */}
      {type === "password" && (
        <button
          type="button"
          onClick={() => setShowPassword((prev) => !prev)}
          className="
            absolute right-4 top-5
            text-white/40
            transition-colors
            hover:text-[#F6E7C8]
          "
        >
          {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
        </button>
      )}

      {/* Bottom Focus Glow */}
      <div
        className="
          pointer-events-none
          absolute bottom-0 left-1/2
          h-px w-0
          -translate-x-1/2
          bg-linear-to-r from-transparent via-[#C7F5D9] to-transparent
          transition-all duration-300
          peer-focus:w-[85%]
        "
      />
    </div>
  );
}