package kerasinoapps.kapps.infona.auth_module.presentation.state

enum class AuthInputErrorType {
    EmptyField,
    PasswordToShort,
    PasswordNoNumber,
    PasswordNoSpecialCharakter,
    PasswordsDoNotMatch,
    EverythingFine,
    EmailDoesntContainAtSymbol
}