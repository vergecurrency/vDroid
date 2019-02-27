package io.horizontalsystems.bitcoinkit.network

import io.horizontalsystems.bitcoinkit.blocks.validators.BitcoinCashValidator
import io.horizontalsystems.bitcoinkit.blocks.validators.BlockValidator
import io.horizontalsystems.bitcoinkit.models.Block
import io.horizontalsystems.bitcoinkit.models.Header
import io.horizontalsystems.bitcoinkit.utils.HashUtils

class MainNet : Network() {

    override var port: Int = 21102

    override var magic: Long = 0xf7a77effL
    override var bip32HeaderPub: Int = 0x022d2533
    override var bip32HeaderPriv: Int = 0x0221312b
    override var addressVersion: Int = 0
    override var addressSegwitHrp: String = "verge"
    override var addressScriptVersion: Int = 5
    override var coinType: Int = 0

    override val maxBlockSize = 1024

    override var dnsSeeds: Array<String> = arrayOf(
            "159.89.202.56",
            "138.197.68.130",
            "165.227.31.52",
            "159.89.202.56",
            "188.40.78.31",
            "176.9.143.143",
            "198.27.82.41"
    )

    private val blockHeader = Header().apply {
        version = 4098
        prevHash = HashUtils.toBytesAsLE("00000000016cb1fe51f6a654405eb4361c7dce059b12d5aa53bb142385cfd765")
        merkleHash = HashUtils.toBytesAsLE("96d87a0f5edd18b94ea5854c04c7b2f83ac857fd5a5b162a6dec812820378b77")
        timestamp = 1469587249
        bits = 470113837
        nonce = 1432803291
    }

    override val checkpointBlock = Block(blockHeader, 559478)
    override val blockValidator = BitcoinCashValidator(this)

    override fun validateBlock(block: Block, previousBlock: Block) {
        blockValidator.validate(block, previousBlock)
    }
}
