const API_HOST_PATH = "http://localhost:8080/college";
const Constants = {
  TEST: "TEST",
  API_URLS: {
    REGISTER_ADMIN: `${API_HOST_PATH}/auth/admin/`,
    REGISTER_STUDENT: `${API_HOST_PATH}/auth/student/`,
    REGISTER_EMPLOYEE: `${API_HOST_PATH}/auth/employee/`,
    CONFIRM_STUDENT_ACCOUNT: `${API_HOST_PATH}/register/confirm-account/`,
    RESEND_ACTIVATION: `${API_HOST_PATH}/register/resend-activation/`,
    LOGIN: `${API_HOST_PATH}/app/login`,
    LOGOUT: `${API_HOST_PATH}/app/doLogout/`,
    GET_FORGOT_PASSWORD_TOKEN: `${API_HOST_PATH}/forgotPassword/token/`,
    RESET_FORGOT_PASSWORD: `${API_HOST_PATH}/forgotPassword/reset-password/`,
    REGISTER_INVENTORY_COMPLAINT: `${API_HOST_PATH}/inventory/register-complaint/`,
    INVENTORY_COMPLAINT_RESOLVED: `${API_HOST_PATH}/inventory/complaint-resolved/`,
    GET_SEAT_INFO_BY_EMPID: `${API_HOST_PATH}/seatAllocation/getseatinfo/`,
    GET_ALL_LOC_SEAT: `${API_HOST_PATH}/seatAllocation/allocateseat/`,
    GET_ALL_DELOC_SEAT: `${API_HOST_PATH}/seatAllocation/deallocateseat/`,
    GET_ALL_SEATS: `${API_HOST_PATH}/seatAllocation/getallseats/`,
    GET_ALL_PROJCTS_ROOM: `${API_HOST_PATH}/master/getAllProjectRooms/`,
    GET_PIECHART_ROOM: `${API_HOST_PATH}/seatAllocation/getseatroomoccupancy/`,
    GET_BARCHART_DATA: `${API_HOST_PATH}/seatAllocation/getseatroomoccupancy/`,
    GET_STUDENTS_DETAILS: `${API_HOST_PATH}/admin/home/students/`,
    GET_EMPLOYEES_DETAILS: `${API_HOST_PATH}/admin/home/employees/`,
    ACTIVATE_STUDENT_BY_ID: `${API_HOST_PATH}/admin/home/activateStudent/`,
    ACTIVATE_EMPLOYEE_BY_ID: `${API_HOST_PATH}/admin/home/activateEmployee/`,
    DEACTIVATE_STUDENT_BY_ID: `${API_HOST_PATH}/admin/home/de-activateStudent/`,
    DEACTIVATE_EMPLOYEE_BY_ID: `${API_HOST_PATH}/admin/home/de-activateStudent/`,
  },
};

export default Constants;
