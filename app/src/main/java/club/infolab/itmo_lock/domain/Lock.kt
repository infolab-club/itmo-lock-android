package club.infolab.itmo_lock.domain

interface Lock {
    fun unlock(token: List<Char>)

    fun lock(token: List<Char>)
}