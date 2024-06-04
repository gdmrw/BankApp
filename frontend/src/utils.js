const domain = "http://localhost:8080";

export const login = (credentials) => {
  const loginUrl = `${domain}/login`;

  return fetch(loginUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: new URLSearchParams({
      username: credentials.username,
      password: credentials.password,
    }),
    credentials: "include", // This is crucial for maintaining the session
  }).then((response) => {
    if (response.status < 200 || response.status >= 300) {
      throw Error("Fail to log in");
    }
  });
};

export const signup = (data) => {
  const signupUrl = `${domain}/register`;

  return fetch(signupUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: new URLSearchParams({
      username: data.username,
      password: data.password,
      balance: data.initial_balance,
    }),
    credentials: "include", // This is crucial for maintaining the session
  }).then((response) => {
    if (response.status < 200 || response.status >= 300) {
      throw Error("Fail to sign up");
    }
  });
};

export const withdraw = (amount) => {
  const withdrawUrl = `${domain}/withdraw`;

  return fetch(withdrawUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: new URLSearchParams({
      amount: amount,
    }),
    credentials: "include", // This is crucial for maintaining the session
  })
    .then((response) => {
      if (response.status < 200 || response.status >= 300) {
        throw Error("Fail to withdraw");
      }
      return response.json();
    })
    .then((data) => {
      return data.newBalance;
    });
};

export const deposit = (amount) => {
  const depositUrl = `${domain}/deposit`;

  return fetch(depositUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: new URLSearchParams({
      amount: amount,
    }),
    credentials: "include", // This is crucial for maintaining the session
  })
    .then((response) => {
      if (response.status < 200 || response.status >= 300) {
        throw Error("Fail to deposit");
      }
      return response.json();
    })
    .then((data) => {
      return data.newBalance;
    });
};

export const checkBalance = () => {
  const checkBalanceUrl = `${domain}/balance`;

  return fetch(checkBalanceUrl, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include", // This is crucial for maintaining the session
  })
    .then((response) => {
      if (response.status < 200 || response.status >= 300) {
        throw Error("Fail to check balance");
      }
      return response.json();
    })
    .then((data) => {
      return data;
    });
};

export const logout = () => {
  const logoutUrl = `${domain}/logout`;

  return fetch(logoutUrl, {
    method: "GET",
    credentials: "include",
  }).then((response) => {
    if (response.status < 200 || response.status >= 300) {
      throw Error("Fail to log out");
    }
  });
};
