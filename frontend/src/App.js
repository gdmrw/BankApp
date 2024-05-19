import { useState } from "react";
import { Layout, Typography, Button, message } from "antd";
import LoginForm from "./components/LoginForm";
import SignupForm from "./components/SignupForm";
import BankForm from "./components/BankForm";
import { logout } from "./utils";
import "./App.css";

const { Header, Content } = Layout;
const { Title } = Typography;

function App() {
  const [authed, setAuthed] = useState(false);

  const handleLogin = () => {
    setAuthed(true);
  };

  const handleLogout = () => {
    logout()
      .then(() => {
        message.success("Successfully logged out");
        setAuthed(false);
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  return (
    <Layout style={{ height: "100vh" }}>
      <Header>
        <div
          className="header"
          style={{ display: "flex", justifyContent: "space-between" }}
        >
          <Title level={2} style={{ color: "white" }}>
            Bank App
          </Title>
          <div>
            {authed ? (
              <Button shape="round" type="primary" onClick={handleLogout}>
                Logout
              </Button>
            ) : (
              <SignupForm />
            )}
          </div>
        </div>
      </Header>
      <Content
        style={{
          padding: "50px",
          maxHeight: "calc(100% - 64px)",
          overflowY: "auto",
        }}
      >
        {!authed ? <LoginForm onLogin={handleLogin} /> : <BankForm />}
      </Content>
    </Layout>
  );
}

export default App;
