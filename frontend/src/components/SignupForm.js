import { Button, Form, Input, message, Modal } from "antd";
import React from "react";
import { signup } from "../utils";

class SignupForm extends React.Component {
  state = {
    displayModal: false,
  };

  handleCancel = () => {
    this.setState({
      displayModal: false,
    });
  };

  signupOnClick = () => {
    this.setState({
      displayModal: true,
    });
  };

  onFinish = (data) => {
    signup(data)
      .then(() => {
        this.setState({
          displayModal: false,
        });
        message.success(`Successfully signed up`);
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  render = () => {
    return (
      <>
        <Button shape="round" type="primary" onClick={this.signupOnClick}>
          Register
        </Button>
        <Modal
          title="Register"
          open={this.state.displayModal}
          onCancel={this.handleCancel}
          footer={null}
          destroyOnClose={true}
        >
          <Form
            name="normal_register"
            initialValues={{ remember: true }}
            onFinish={this.onFinish}
            preserve={false}
          >
            <Form.Item
              label="Username"
              name="username"
              rules={[
                { required: true, message: "Please input your username!" },
              ]}
            >
              <Input placeholder="Username" />
            </Form.Item>
            <Form.Item
              label="Password"
              name="password"
              rules={[
                { required: true, message: "Please input your password!" },
              ]}
            >
              <Input.Password placeholder="Password" />
            </Form.Item>
            <Form.Item
              label="Balance"
              name="initial_balance"
              rules={[
                {
                  required: true,
                  message: "Please input your initial balance!",
                },
              ]}
            >
              <Input placeholder="Initial Balance" />
            </Form.Item>

            <Form.Item>
              <Button type="primary" htmlType="submit">
                Register
              </Button>
            </Form.Item>
          </Form>
        </Modal>
      </>
    );
  };
}

export default SignupForm;
