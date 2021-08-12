package club.infolab.itmo_lock.lock

interface Lock {
    fun unlock(token: List<Char>)

    fun lock(token: List<Char>)
}