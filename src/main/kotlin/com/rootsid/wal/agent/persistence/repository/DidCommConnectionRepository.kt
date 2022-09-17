package com.rootsid.wal.agent.persistence.repository

import com.rootsid.wal.agent.persistence.model.DidCommConnectionEntity
import org.springframework.data.repository.CrudRepository

sealed interface DidCommConnectionRepository : CrudRepository<DidCommConnectionEntity, String> {}
