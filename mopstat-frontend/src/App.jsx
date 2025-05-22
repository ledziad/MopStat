import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import DogsPage from "./pages/DogsPage";
import AddDogPage from "./pages/AddDogPage";
import DogDetailsPage from "./pages/DogDetailsPage";
import AddRecordPage from "./pages/AddRecordPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        {/* inne ścieżki */}
        <Route path="*" element={<Navigate to="/login" />} />
                <Route path="/dogs" element={<DogsPage />} />
                <Route path="/add-dog" element={<AddDogPage />} />
                <Route path="/dogs/:id" element={<DogDetailsPage />} />
                <Route path="/add-record/:dogId" element={<AddRecordPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
