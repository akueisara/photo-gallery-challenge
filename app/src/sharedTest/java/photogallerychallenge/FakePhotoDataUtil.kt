package com.example.photogallerychallenge

import com.example.photogallerychallenge.data.model.*

fun getFakePhoto(id: String): Photo {
    val urls = Urls(
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
        null,
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
        null,
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjk3OTUyfQ"
    )

    val links = Links(
        "https://api.unsplash.com/photos/TYpX940GS_U",
        "https://unsplash.com/photos/TYpX940GS_U",
        null,
        null,
        null,
        "https://unsplash.com/photos/TYpX940GS_U/download",
        "https://api.unsplash.com/photos/TYpX940GS_U/download",
        null,
        null
    )

    val userLinks = Links(
        "https://api.unsplash.com/users/danesduet",
        "https://unsplash.com/@danesduet",
        "https://api.unsplash.com/users/danesduet/photos",
        "https://api.unsplash.com/users/danesduet/likes",
        "https://api.unsplash.com/users/danesduet/portfolio",
        null,
        null,
        "https://api.unsplash.com/users/danesduet/following",
        "https://api.unsplash.com/users/danesduet/followers"
    )

    val userImageUrls = Urls(
        null,
        "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32",
        "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64",
        null,
        "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128",
        null,
        null
    )

    val user = User(
        "WqEryhdhOsY",
        "2019-10-26T04:42:06-04:00",
        "danesduet",
        "Daniel Olah",
        "danesduet",
        null,
        "Capturing the future",
        "Budapest, Hungary",
        userLinks,
        userImageUrls,
        107,
        94,
        0,
        true
    )
    return Photo(
        id, "2019-10-26T02:47:19-04:00",
        "2019-10-26T04:23:34-04:00", "2019-10-26T04:23:34-04:00",
        2026, 3602, "#233961", "Infinity Dunes - Abu Dhabi Desert", null,
        urls, links, 70, false, user, null, null, null, null
    )
}

fun getFakeDatabasePhoto(id: String): DatabasePhoto {
    val urls = Urls(
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
        null,
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
        null,
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjk3OTUyfQ",
        "https://images.unsplash.com/photo-1572072393749-3ca9c8ea0831?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjk3OTUyfQ"
    )

    val links = Links(
        "https://api.unsplash.com/photos/TYpX940GS_U",
        "https://unsplash.com/photos/TYpX940GS_U",
        null,
        null,
        null,
        "https://unsplash.com/photos/TYpX940GS_U/download",
        "https://api.unsplash.com/photos/TYpX940GS_U/download",
        null,
        null
    )

    val userLinks = Links(
        "https://api.unsplash.com/users/danesduet",
        "https://unsplash.com/@danesduet",
        "https://api.unsplash.com/users/danesduet/photos",
        "https://api.unsplash.com/users/danesduet/likes",
        "https://api.unsplash.com/users/danesduet/portfolio",
        null,
        null,
        "https://api.unsplash.com/users/danesduet/following",
        "https://api.unsplash.com/users/danesduet/followers"
    )

    val userImageUrls = Urls(
        null,
        "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32",
        "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64",
        null,
        "https://images.unsplash.com/profile-1528009924690-123902d73e88?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128",
        null,
        null
    )

    val user = User(
        "WqEryhdhOsY",
        "2019-10-26T04:42:06-04:00",
        "danesduet",
        "Daniel Olah",
        "danesduet",
        null,
        "Capturing the future",
        "Budapest, Hungary",
        userLinks,
        userImageUrls,
        107,
        94,
        0,
        true
    )

    val exif = ExtraInfo("NIKON CORPORATION", "NIKON D850", "20.0", "2.5", "1/160", 1250)

    val position = Position(null, null)

    val location = Location(null, null, "Taipeu", "Taiwan", position)


    return DatabasePhoto(
        id, "2019-10-26T02:47:19-04:00",
        "2019-10-26T04:23:34-04:00", "2019-10-26T04:23:34-04:00",
        2026, 3602, "#233961", "Infinity Dunes - Abu Dhabi Desert", null,
        urls, links, 70, false, user.id, user.name, user.username, user.profile_image.small, exif, location, 2313411, 7998)
}