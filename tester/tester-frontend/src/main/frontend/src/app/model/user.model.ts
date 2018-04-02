export class User {
    constructor(
        public password: string,
        public email: string,
        public role: string,
        public firstname: string,
        public lastname: string,
        public school: string,
    ){}
}