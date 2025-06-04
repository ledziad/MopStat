import { useEffect } from "react";

export default function FriendlyAlert({ message, type = "success", onClose }) {
  useEffect(() => {
    if (message) {
      const timeout = setTimeout(onClose, 2300);
      return () => clearTimeout(timeout);
    }
  }, [message, onClose]);
  if (!message) return null;
  return (
    <div className={`friendly-alert ${type}`} onClick={onClose}>
      {message}
    </div>
  );
}
