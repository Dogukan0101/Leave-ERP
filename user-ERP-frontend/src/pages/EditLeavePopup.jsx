import { Datepicker } from "flowbite-react";
import React from "react";
import { useState, useEffect } from "react";
import { toast } from "react-toastify";


const EditLeavePopup = ({ closePopup, leave }) => {
  const [leaveStart, setLeaveStart] = useState(new Date(leave.startDate));

  const [leaveEnd, setLeaveEnd] = useState(new Date(leave.endDate));

  const [user, setUser] = useState(null);

  const api_url = import.meta.env.VITE_API_URL

  const fetchUserById = async () => {
    try {
      const response = await fetch(
        `${api_url}/users/findUserById?userId=` + leave.user.id,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      setUser(data);

    } catch (error) {
      console.log(error.message);
    }
  };

  const handleUpdateLeave = async (event) => {
    event.preventDefault();

    let diffInDays = Math.round(
      (leaveEnd.getTime() - leaveStart.getTime()) / (1000 * 3600 * 24)
    );

    if (user.restDay + leave.days < diffInDays) {
      toast.info("The user do not have enough leave days.");
      return;
    }

    const leaveData = {
      id: leave.id,
      startDate: leaveStart,
      endDate: leaveEnd,
      days: diffInDays,
      user: {
        id: user.id,
      },
    };

    try {
      const response = await fetch(
        `${api_url}/leaves/updateLeaveById`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(leaveData),
        }
      );

      if(response.status==409){
        toast.warn("There is already a leave whose dates conflict with these dates!");
        return;
      }

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

  const handleDeleteLeave = async (event) => {
    event.preventDefault();

    const confirmDelete = window.confirm(
      "Are you sure you want to delete the leave?"
    );
    if (!confirmDelete) {
      return;
    }

    closePopup();

    try {
      const response = await fetch(
        `${api_url}/leaves/deleteLeaveById?leaveId=` + leave.id,
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

      toast.success("The leave is deleted successfully!");
      window.location.reload();
    } catch (error) {
      toast.error("An error occurred while deleting the user.");
    }
  };

  useEffect(() => {
    fetchUserById();
  }, []);

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
              Edit Leave
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
              <label>Start Date</label>
              <Datepicker
              defaultDate={leaveStart}
                value={leaveStart}
                minDate={new Date()}
                maxDate={new Date(leaveEnd.getTime() - 24 * 60 * 60 * 1000)}
                onSelectedDateChanged={(date) => {
                  setLeaveStart(date);
                }}
              />
            </div>

            <div className="grid gap-4 mb-4 ">
              <label>End Date</label>
              <Datepicker
                defaultDate={leaveEnd}
                value={leaveEnd}
                minDate={new Date(leaveStart.getTime() + 24 * 60 * 60 * 1000)}
                maxDate={
                  new Date(leaveEnd.getTime() + 24 * 60 * 60 * 1000 * 365)
                }
                onSelectedDateChanged={(date) => {
                  setLeaveEnd(date);
                }}
              />
            </div>

            <div className="flex flex-row justify-between">
              <button
                onClick={(event) => handleUpdateLeave(event)}
                type="submit"
                className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50"
              >
                Edit Leave
              </button>

              <button
                onClick={(event) => handleDeleteLeave(event)}
                className="bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50"
              >
                Delete Leave
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EditLeavePopup;
