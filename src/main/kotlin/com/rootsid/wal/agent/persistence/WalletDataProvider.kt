package com.rootsid.wal.agent.persistence

import com.rootsid.wal.agent.exception.ResourceNotFoundException
import com.rootsid.wal.agent.persistence.model.WalletEntity
import com.rootsid.wal.library.dlt.model.Did
import com.rootsid.wal.library.wallet.model.Wallet
import com.rootsid.wal.library.wallet.storage.WalletStorage
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import java.util.*


@Service
class WalletDataProvider(private val walletRepository: WalletRepository) : WalletStorage {
    override fun createWalletObject(walletId: String, seed: String): WalletEntity = WalletEntity(walletId, seed)

    /**
     * Insert wallet
     *
     * @param wallet Wallet data object to add into the database
     * @return true if the operation was acknowledged
     */
    override fun insert(wallet: Wallet): Wallet {
        return walletRepository.save(wallet as WalletEntity)
    }

    /**
     * Update wallet
     *
     * @param wallet updated Wallet data object
     * @return true if the operation was acknowledged
     */
    override fun update(wallet: Wallet): Boolean {
        insert(wallet)
        return true
    }

    /**
     * Find wallet
     *
     * @param walletId name of the wallet to find
     * @return wallet data object
     */
    override fun findById(walletId: String): Wallet {
        return walletRepository.findById(walletId).orElseThrow { Exception("Wallet with id=[$walletId] not found.") }
    }

    /**
     * List wallets
     *
     * @return list of stored wallet names
     */
    override fun list(): List<Wallet> {
        return walletRepository.findAll().toList()
    }

    /**
     * Wallet exists
     *
     * @param walletId name of the wallet to find
     * @return true if the wallet was found
     */
    override fun exists(walletId: String): Boolean {
        return walletRepository.findById(walletId).isPresent
    }

    override fun findDidByAlias(walletId: String, alias: String): Optional<Did> {
        return walletRepository.findDidByAlias(walletId, alias)
            .orElseThrow { ResourceNotFoundException("Wallet with id=[$walletId] not found") }
            .dids.stream().findFirst()
    }

    override fun listDids(walletId: String): List<Did> {
        return findById(walletId).dids
    }

    /**
     * Did alias exists
     *
     * @param walletId name of the wallet storing the did
     * @param didAlias alias of the did
     * @return true if the did was found
     */
    fun didAliasExists(walletId: String, didAlias: String): Boolean {
//        val wallet = walletCollection.findOne("{_id:'$walletId','dids':{${MongoOperator.elemMatch}: {'alias':'$didAlias'}}}")
//        return wallet != null
        return true
    }

    /**
     * Key id exists
     *
     * @param walletId name of the wallet storing the did
     * @param didAlias alias of the did
     * @param keyId key identifier
     * @return true if the keyId was found
     */
    fun keyIdExists(walletId: String, didAlias: String, keyId: String): Boolean {
//        val wallet =
//            walletCollection.findOne("{_id:'$walletId','dids':{${MongoOperator.elemMatch}: {'alias':'$didAlias'}}, 'dids.keyPairs.keyId':'$keyId'}")
//        return wallet != null
        return true
    }

    /**
     * Issued credential alias exists
     *
     * @param issuedCredentialAlias credential alias to find
     * @return true if the did was found
     */
    fun issuedCredentialAliasExists(walletName: String, issuedCredentialAlias: String): Boolean {
//        val wallet =
//            walletCollection.findOne("{_id:'$walletName','issuedCredentials':{${MongoOperator.elemMatch}: {'alias':'$issuedCredentialAlias'}}}")
//        return wallet != null

        return true
    }

    /**
     * Credential alias exists
     *
     * @param credentialAlias credential alias to find
     * @return true if the did was found
     */
    fun credentialAliasExists(walletId: String, credentialAlias: String): Boolean {
//        val wallet = walletCollection.findOne("{_id:'$walletId','credentials':{${MongoOperator.elemMatch}: {'alias':'$credentialAlias'}}}")
//        return wallet != null

        return true
    }
}

interface WalletRepository : CrudRepository<WalletEntity, String> {
    @Query(value = "{ _id: :#{#walletId}, dids: { \$elemMatch: { alias: :#{#didAlias} } } }")
    fun findDidByAlias(@Param("walletId") walletId: String, @Param("didAlias") didAlias: String): Optional<WalletEntity>
}
