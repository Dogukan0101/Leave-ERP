import { Datepicker } from "flowbite-react";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";

const AddLeavePopup = ({ closePopup, user }) => {

  const [leaveStart, setLeaveStart] = useState(new Date());

  const [leaveEnd, setLeaveEnd] = useState(
    new Date(leaveStart.getTime() + 24 * 60 * 60 * 1000)
  );

  const handleAddLeave = async (event) => {
    event.preventDefault();

    let diffInDays = Math.round(
      (leaveEnd.getTime() - leaveStart.getTime()) / (1000 * 3600 * 24)
    );

    if (diffInDays > user.restDay) {
      toast.info("The user do not have enough leave days.");
      return;
    }

    const leaveData = {
      startDate: leaveStart,
      endDate: leaveEnd,
      days: diffInDays,
      user: {
        id: user.id,
      },
    };

    try {
      const response = await fetch("http://localhost:8080/leaves/createLeave", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(leaveData),
      });

      if(response.status==400){
        toast.warn("There is already a leave whose dates conflict with these dates!");
        return;
      }

      if (!response.ok) {
        throw new Error(
          `HTTP error! status: ${response.status}`
        );
      }

      const result = await response.json();

      console.log("Success:", result);
    } catch (error) {
      console.error("Error:", error);
    }

   window.location.reload();
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
              Create Leave for {user.fullName}
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
                maxDate={
                  new Date(leaveEnd.getTime() - 24 * 60 * 60 * 1000)
                }
                onSelectedDateChanged={(date) => {
                  setLeaveStart(date);
                }}
              />
            </div>

            <div className="grid gap-4 mb-4 ">
              <label>End Date</label>
              <Datepicker
                minDate={new Date(leaveStart.getTime() + 24 * 60 * 60 * 1000)}
                maxDate={
                  new Date(leaveEnd.getTime() + 24 * 60 * 60 * 1000 * 365)
                }
                onSelectedDateChanged={(date) => {
                  setLeaveEnd(date);
                }}
              />
            </div>

            <button
              onClick={(event) => handleAddLeave(event)}
              type="submit"
              className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50"
            >
              Create Leave
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddLeavePopup;
