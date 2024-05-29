package com.poklad.giphyjob.domain.base

interface Mapper<Source, Destination> {
    fun mapping(data: Source): Destination
}
