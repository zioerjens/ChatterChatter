export class ChangePassword {
  password: string;
  passwordRepeat: string;

  constructor(password: string, passwordRepeat:string){
    this.password = password;
    this.passwordRepeat = passwordRepeat;
  }

}
