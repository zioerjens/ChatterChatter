export class UserRegistration {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  passwordRepeat: string;

  constructor(username: string, firstName: string, lastName:string, email:string, password:string, passwordRepeat:string){
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.passwordRepeat = passwordRepeat;
  }

}
