package club.infolab.itmo_lock.domain

/* Returns true if there is incorrect input string */
fun String.isWrongInputName(): Boolean {
    return this == ""
}

/* Returns true if there is incorrect password */
fun String.isWrongInputPassword(): Boolean {
    return this.length < 8
}