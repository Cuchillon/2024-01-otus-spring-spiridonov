export class RestApiError {
    statusCode: number
    errors: Map<string, string>

    constructor(statusCode: number, errors: Map<string, string>) {
        this.statusCode = statusCode;
        this.errors = errors;
    }
}
