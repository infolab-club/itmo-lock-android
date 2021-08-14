package club.infolab.itmo_lock.domain.usecases.lock

interface Lock {
    fun unlock(token: List<Char>)

    fun lock(token: List<Char>)
}