package club.infolab.itmo_lock.config

import club.infolab.itmo_lock.data.entity.Room

class AppConfig {
    companion object {
        val rooms = arrayListOf(
            Room(
                1, "447",
                "Проход в коворкинг и лекционную.",
                "https://praktik.work/assets/gallery/3/123.jpg"
            )
        )

        const val BASE_URL = "https://itmolock.herokuapp.com/"
    }

}