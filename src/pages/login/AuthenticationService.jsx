class AuthenticationService {
    executeBasicAuthenticationService(username, password) {
        return axios.get(`${API_URL}/oauth/token/`,
            { headers: { authorization: this.createBasicAuthToken(username, password) } })
    }
    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }
}