import { supabase } from "../lib/supabase";

export default function Home() {
  const handleLogout = async () => {
    await supabase.auth.signOut();
  };

  return (
    <div>
      <h1>Home</h1>

      <p>Welcome to the application!</p>

      <button onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
}