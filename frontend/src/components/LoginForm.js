import React from "react";
import { Button, Form, Input, message } from "antd";
import { login } from "../utils"; // Make sure to implement this in your utils

class LoginForm extends React.Component {
  onFinish = (data) => {
    login(data)
      .then(() => {
        this.props.onLogin();
        message.success("Successfully logged in");
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  render = () => (
    <Form name="normal_login" onFinish={this.onFinish}>
      <Form.Item
        label="Username"
        name="username"
        rules={[{ required: true, message: "Please input your username!" }]}
      >
        <Input placeholder="Username" />
      </Form.Item>
      <Form.Item
        label="Password"
        name="password"
        rules={[{ required: true, message: "Please input your password!" }]}
      >
        <Input.Password placeholder="Password" />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Login
        </Button>
      </Form.Item>
    </Form>
  );
}

export default LoginForm;
