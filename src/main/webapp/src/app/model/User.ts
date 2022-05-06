import {UserRoleEnum} from "./enum/user-role.enum";

export class User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  role: UserRoleEnum;

  constructor(id: number, username: string, firstName: string, lastName:string, email:string, role: UserRoleEnum){
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.role = role;
  }

}
