import { Datepicker } from "flowbite-react";
import React from "react";
import { useState, useEffect } from "react";
import { toast } from "react-toastify";

const EditDepartmentPopup = ({ closePopup, department }) => {
  const [name, setName] = useState(department.name);

  const handleUpdateDepartment = async (event) => {
    event.preventDefault();

    const departmentData = {
      id: department.id,
      name: name,
    };

    try {
      const response = await fetch(
        "http://localhost:8080/departments/updateDepartmentById",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(departmentData),
        }
      );

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const result = await response.json();

      console.log("Success:", result);
    } catch (error) {
      console.error("Error:", error);
    }

    window.location.reload();
  };

  const handleDeleteDepartment = async (event) => {
    event.preventDefault();

    const confirmDelete = window.confirm(
      "Are you sure you want to delete the department?"
    );
    if (!confirmDelete) {
      return;
    }

    closePopup();

    try {
      const response = await fetch(
        "http://localhost:8080/departments/deleteDepartmentById?departmentId=" +
          department.id,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      toast.success("The department is deleted successfully!");
      window.location.reload();
    } catch (error) {
      toast.error("An error occurred while deleting the department.");
    }
  };

  return (
    <div
      id="crud-modal"
      tabindex="-1"
      aria-hidden="true"
      class="overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 bottom-0 z-50 flex justify-center items-center bg-gray-800/50"
    >
      <div class="relative p-4 w-full max-w-md max-h-full">
        <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">
          <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
              Edit Department
            </h3>
            <button
              onClick={closePopup}
              type="button"
              class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
              data-modal-toggle="crud-modal"
            >
              <svg
                class="w-3 h-3"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 14 14"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                />
              </svg>
              <span class="sr-only">Close modal</span>
            </button>
          </div>

          <form className="p-4 md:p-5">
            <div className="grid gap-4 mb-4 ">
              <label>Department Name</label>
              <input
                type="text"
                onChange={(event) => setName(event.target.value)}
                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500"
                value={name}
                required
              />
            </div>

            <div className="flex flex-row justify-between">
              <button
                onClick={(event) => handleUpdateDepartment(event)}
                type="submit"
                className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50"
              >
                Edit Department
              </button>

              <button
                onClick={(event) => handleDeleteDepartment(event)}
                className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50"
              >
                Delete Department
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EditDepartmentPopup;
