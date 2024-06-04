import React, { useState } from "react";
import { Button, Form, Input, message, Modal } from "antd";
import { withdraw, deposit, checkBalance } from "../utils"; // Import the utility functions

const BankSystem = () => {
  const [withdrawForm] = Form.useForm();
  const [depositForm] = Form.useForm();
  const [balance, setBalance] = useState("");

  const onWithdrawFinish = (values) => {
    withdraw(values.amount)
      .then((newBalance) => {
        message.success(`Successfully withdrew $${values.amount}`);
        withdrawForm.resetFields(); // Clear the withdraw form fields
        setBalance(newBalance);
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  const onDepositFinish = (values) => {
    deposit(values.amount)
      .then((newBalance) => {
        message.success(`Successfully deposited $${values.amount}`);
        depositForm.resetFields(); // Clear the deposit form fields
        setBalance(newBalance);
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  const handleCheckBalance = () => {
    checkBalance()
      .then((data) => {
        setBalance(data.balance);
        Modal.info({
          title: "Your Balance",
          content: <div>Your balance is: ${data.balance}</div>,
          onOk() {},
        });
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  return (
    <div>
      <h2>Bank System</h2>

      <Form form={withdrawForm} onFinish={onWithdrawFinish}>
        <Form.Item
          label="Withdraw Amount"
          name="amount"
          rules={[
            { required: true, message: "Please input the amount to withdraw!" },
          ]}
        >
          <Input placeholder="Enter amount" />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Withdraw
          </Button>
        </Form.Item>
      </Form>

      <Form form={depositForm} onFinish={onDepositFinish}>
        <Form.Item
          label="Deposit Amount"
          name="amount"
          rules={[
            { required: true, message: "Please input the amount to deposit!" },
          ]}
        >
          <Input placeholder="Enter amount" />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Deposit
          </Button>
        </Form.Item>
      </Form>

      <Button
        onClick={handleCheckBalance}
        type="primary"
        style={{ marginTop: "20px" }}
      >
        Check Balance
      </Button>
    </div>
  );
};

export default BankSystem;
