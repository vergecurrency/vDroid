package vergecurrency.vergewallet.wallet

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import vergecurrency.vergewallet.service.model.WatchRequestCredentials
import vergecurrency.vergewallet.service.model.wallet.*
import vergecurrency.vergewallet.wallet.int.WalletClientInterface

internal class TxTransponderTest {


    fun testCreateATransactionProposal() {
        val walletClient = TxTransponderTest1WalletClient()
        val TxTransponder = TxTransponder(walletClient)
    }








    open class TxTransponderTest1WalletClient: WalletClientMock() {
        override fun createTxProposal(proposal: TxProposal, completion: (TxProposalResponse?, TxProposalErrorResponse?, Exception?) -> Unit) {
            var responseJson = "{\"walletM\":1,\"outputs\":[{\"message\":null,\"toAddress\":\"DMTtEgS4JecxRhdTABPsgDJizPMAuTZFeU\"," +
            "\"stealth\":false,\"amount\":10000000}],\"fee\":100000,\"coin\":\"xvg\",\"actions\":[],\"walletId\":\"fbd0c3d0-465c-4f4e-a890-d0e4bdb606e0\"," +
                    "\"id\":\"b0aa5392-2e16-4dd6-a83a-80bff0dae927\",\"payProUrl\":null,\"feeLevel\":\"normal\",\"version\":3,\"message\":null,\"walletN\":1," +
                    "\"requiredSignatures\":1,\"excludeUnconfirmedUtxos\":false,\"network\":\"livenet\",\"timestamp\":1555086257,\"amount\":10000000," +
                    "\"inputs\":[{\"publicKeys\":[\"033036ed064cf98b70f0f9f62db8479f344c50d2b8832808c1bf9761f1fc2f4aa7\"],\"txid\":\"b2a146585bc6bbe97db73c0cd0e38308ac38eb1cb75c89746af1dade9c6400b9\"," +
                    "\"path\":\"m\\/0\\/8\",\"amount\":0.10100000000000001,\"vout\":0,\"locked\":false,\"address\":\"DAjo2HJRR2kSi3yvxQyhLTrFWobfnJnbxc\",\"satoshis\":10100000," +
                    "\"scriptPubKey\":\"76a9143d6890b4fffd829c31e64bf950fbefd2a8f1180d88ac\",\"confirmations\":78174}],\"createdOn\":1555086257,\"outputOrder\":[1,0]," +
                    "\"requiredRejections\":1,\"feePerKb\":100000,\"addressType\":\"P2PKH\",\"creatorId\":\"566ea30247a20870c08b4db599fa1039ea00b55779942b788196709a702602cd\"," +
                    "\"changeAddress\":{\"beRegistered\":null,\"publicKeys\":[\"0301714f0f46c402de5240063165c1dbcb018af72fb0381c8ae8eca765942e62f1\"],\"isChange\":true," +
                    "\"type\":\"P2PKH\",\"createdOn\":1555086257,\"path\":\"m\\/1\\/81\",\"version\":\"1.0.0\",\"network\":\"livenet\",\"_id\":\"5cb0bbb1616c2628658f7d43\"," +
                    "\"coin\":\"xvg\",\"address\":\"D7RMYX9r7vfe7piGwFbe1artpmxcLRHz1y\",\"walletId\":\"fbd0c3d0-465c-4f4e-a890-d0e4bdb606e0\"},\"inputPaths\":[\"m\\/0\\/8\"],\"status\":\"temporary\"}"

            var response = TxProposalResponse.decode(responseJson)

                completion(response, null, null)
            }
    }

    class TxTransponderTest2WalletClient: WalletClientMock() {
        override fun createTxProposal(proposal: TxProposal, completion:  TxProposalCompletion) {
            var responseJson = "{\"message\":\"Invalid address\",\"code\":\"INVALID_ADDRESS\"}"
            var response = TxProposalErrorResponse.decode(responseJson)

                completion(null, response, null)
            }
    }

    class TxTransponderTest3WalletClient: TxTransponderTest1WalletClient() {
        override fun publishTxProposal(txp: TxProposalResponse, completion: TxProposalCompletion) {
            var responseJson = "{\"walletM\":1,\"version\":3,\"fee\":100000,\"requiredRejections\":1,\"" +
            "creatorName\":\"{\\\"iv\\\":\\\"eB2o+MVCryZtGlx5DuXY9A==\\\",\\\"v\\\":1,\\\"iter\\\":1,\\\"ks\\\":128,\\\"ts\\\":64,\\\"mode\\\":\\\"ccm\\\",\\\"adata\\\":\\\"\\\",\\\"cipher\\\":\\\"aes\\\",\\\"ct\\\":\\\"1P2zc0PomM9GfWQDkZ4Aleyn4Q==\\\"}\"," +
                    "\"walletN\":1,\"id\":\"b7b96d6d-e5ef-4026-9397-68cac55307fe\",\"changeAddress\":{\"path\":\"m\\/1\\/3\",\"version\":\"1.0.0\",\"coin\":\"xvg\"," +
                    "\"publicKeys\":[\"0359bb7d586cc4c245d0232f9187029faf76b593c1e2dba82eba2c000ed416c168\"],\"_id\":\"5cb1bebd616c2628658f7d63\"," +
                    "\"hasActivity\":null,\"type\":\"P2PKH\",\"network\":\"livenet\",\"beRegistered\":null,\"createdOn\":1555152573," +
                    "\"address\":\"DHjThJe4M3Kpvr7b8BKifsTLQygFMpq7g3\",\"isChange\":true,\"walletId\":\"3547a0ad-2273-4403-ae38-dd0ecdf86d4f\"}," +
                    "\"inputs\":[{\"amount\":0.10100000000000001,\"address\":\"DQ8TVJGrVPQEYQiGMwnUvP15MdTFP8yBXJ\",\"vout\":0,\"locked\":false," +
                    "\"path\":\"m\\/0\\/0\",\"confirmations\":13,\"txid\":\"442c938c6c258f62ab349b5273b00e190af89c96faa3e8eee7941dfb38e85be0\"," +
                    "\"satoshis\":10100000,\"publicKeys\":[\"031a661e0ee71dc72abcfe37d069bcf41b88448f6955ac75fdc4c6c9ea44480098\"]," +
                    "\"scriptPubKey\":\"76a914d04b76146225d93e206f8c34b461403791b7264288ac\"}],\"network\":\"livenet\",\"createdOn\":1555152573," +
                    "\"timestamp\":1555152573,\"customData\":null,\"feeLevel\":\"normal\",\"payProUrl\":null,\"requiredSignatures\":1," +
                    "\"inputPaths\":[\"m\\/0\\/0\"],\"feePerKb\":100000,\"proposalSignature\":\"3045022100fe0728dedbdf2b6b59a737530a676da0df3d16b09461472ab60b31101fca87f102202ae5a8c588390dca7f5ba06c632044e49803526c76ac211457dc75bccc371ab9\"," +
                    "\"actions\":[],\"amount\":10000000,\"status\":\"pending\",\"creatorId\":\"8830a04d48c0dcded327806fc903b0f9b5ada46d9a12e0796da40b80e5a2b72c\"," +
                    "\"outputOrder\":[0,1],\"derivationStrategy\":\"BIP44\",\"addressType\":\"P2PKH\",\"coin\":\"xvg\",\"message\":null,\"excludeUnconfirmedUtxos\":false," +
                    "\"outputs\":[{\"toAddress\":\"D5L9sbg1RMPS8yuVpw6jA3Cc1CpYH1shgk\",\"message\":null,\"stealth\":false,\"amount\":10000000}],\"walletId\":\"3547a0ad-2273-4403-ae38-dd0ecdf86d4f\"}"

            var response = TxProposalResponse.decode(responseJson)

                completion(response, null, null)
            }

        override fun signTxProposal(txp: TxProposalResponse, completion: TxProposalCompletion) {
            var responseJson = "{\"payProUrl\":null,\"requiredRejections\":1,\"coin\":\"xvg\",\"excludeUnconfirmedUtxos\":false," +
            "\"creatorId\":\"8830a04d48c0dcded327806fc903b0f9b5ada46d9a12e0796da40b80e5a2b72c\",\"id\":\"e72711ed-4ada-4ee3-9d60-deb2ed052f39\"," +
                    "\"inputs\":[{\"publicKeys\":[\"031a661e0ee71dc72abcfe37d069bcf41b88448f6955ac75fdc4c6c9ea44480098\"],\"satoshis\":10100000," +
                    "\"txid\":\"442c938c6c258f62ab349b5273b00e190af89c96faa3e8eee7941dfb38e85be0\",\"confirmations\":28,\"scriptPubKey\":\"76a914d04b76146225d93e206f8c34b461403791b7264288ac\"," +
                    "\"vout\":0,\"address\":\"DQ8TVJGrVPQEYQiGMwnUvP15MdTFP8yBXJ\",\"amount\":0.10100000000000001,\"path\":\"m\\/0\\/0\"," +
                    "\"locked\":false}],\"addressType\":\"P2PKH\",\"timestamp\":1555153186,\"outputOrder\":[0,1],\"txid\":\"b92dcf62d353a25a456adaf6c2e2159747f78e7074d2afa3d5c7e659a8a4cee6\"," +
                    "\"changeAddress\":{\"_id\":\"5cb1c122616c2628658f7d66\",\"walletId\":\"3547a0ad-2273-4403-ae38-dd0ecdf86d4f\",\"isChange\":true," +
                    "\"beRegistered\":null,\"createdOn\":1555153186,\"coin\":\"xvg\",\"network\":\"livenet\",\"version\":\"1.0.0\",\"address\":\"DQsYoSg3h78zMFiifGH3UfneLw7i1CAkmy\"," +
                    "\"type\":\"P2PKH\",\"hasActivity\":null,\"publicKeys\":[\"039d9f41b75ab3baaadd8f3e06b7a37797d84cf00bf358d89cb50efb172b101ad5\"]," +
                    "\"path\":\"m\\/1\\/4\"},\"status\":\"accepted\",\"creatorName\":\"{\\\"iv\\\":\\\"eB2o+MVCryZtGlx5DuXY9A==\\\",\\\"v\\\":1,\\\"iter\\\":1,\\\"ks\\\":128,\\\"ts\\\":64,\\\"mode\\\":\\\"ccm\\\",\\\"adata\\\":\\\"\\\",\\\"cipher\\\":\\\"aes\\\",\\\"ct\\\":\\\"1P2zc0PomM9GfWQDkZ4Aleyn4Q==\\\"}\"," +
                    "\"feePerKb\":100000,\"proposalSignaturePubKeySig\":null,\"walletId\":\"3547a0ad-2273-4403-ae38-dd0ecdf86d4f\",\"version\":3," +
                    "\"actions\":[{\"type\":\"accept\",\"signatures\":[\"30450221008c222a0edf83275e5849d62cb09e57554d96ab643d9ab7af26021f3f9921f8ba02201247b030df5f8f734c679abf1a4e5cb26e3e2824b142a7792f0ed2dc5faf456d\"]," +
                    "\"xpub\":\"ToEA6kpDTfS2mToqS7yg6xzX6kSj8spwrq611jkdGL4fFitpGg8s4uFC5WpQ5839tYMnSbkc59YB23w4sRw2jvpyygBbb9YAcSUqiRuCyicQVz4\"," +
                    "\"copayerId\":\"8830a04d48c0dcded327806fc903b0f9b5ada46d9a12e0796da40b80e5a2b72c\",\"version\":\"1.0.0\",\"createdOn\":1555153191," +
                    "\"comment\":null}],\"requiredSignatures\":1,\"outputs\":[{\"amount\":10000000,\"toAddress\":\"D5L9sbg1RMPS8yuVpw6jA3Cc1CpYH1shgk\"," +
                    "\"message\":null,\"stealth\":false}],\"proposalSignaturePubKey\":null,\"network\":\"livenet\",\"createdOn\":1555153186," +
                    "\"derivationStrategy\":\"BIP44\",\"broadcastedOn\":null,\"walletN\":1,\"inputPaths\":[\"m\\/0\\/0\"],\"fee\":100000," +
                    "\"amount\":10000000,\"message\":null,\"proposalSignature\":\"304402200db38b10cf977e5aa4f142168a3846c90291741cb9b224f1758c73e674bfa59c02204cb4a07a435a71b5b38cb9b89f28a06063afd889737eb488b26ca88f18c5c40b\"," +
                    "\"walletM\":1,\"feeLevel\":\"normal\",\"customData\":null,\"raw\":\"0100000022c1b15c01e05be838fb1d94e7eee8a3fa969cf80a190eb073529b34ab628f256c8c932c44000000006b4830450221008c222a0edf83275e5849d62cb09e57554d96ab643d9ab7af26021f3f9921f8ba02201247b030df5f8f734c679abf1a4e5cb26e3e2824b142a7792f0ed2dc5faf456d0121031a661e0ee71dc72abcfe37d069bcf41b88448f6955ac75fdc4c6c9ea44480098ffffffff0180969800000000001976a91402175bb34e8df3dd2644221a6df2c564c4ff90db88ac00000000\"}"

            var response = TxProposalResponse.decode(responseJson)

                completion(response, null, null)
            }

        override fun broadcastTxProposal(txp: TxProposalResponse, completion: TxProposalCompletion) {
            var responseJson = "{\"outputOrder\":[0,1],\"excludeUnconfirmedUtxos\":false,\"creatorName\":\"{\\\"iv\\\":\\\"eB2o+MVCryZtGlx5DuXY9A==\\\",\\\"v\\\":1,\\\"iter\\\":1,\\\"ks\\\":128,\\\"ts\\\":64,\\\"mode\\\":\\\"ccm\\\",\\\"adata\\\":\\\"\\\",\\\"cipher\\\":\\\"aes\\\",\\\"ct\\\":\\\"1P2zc0PomM9GfWQDkZ4Aleyn4Q==\\\"}\"," +
            "\"derivationStrategy\":\"BIP44\",\"requiredSignatures\":1,\"outputs\":[{\"amount\":10000000,\"toAddress\":\"D5L9sbg1RMPS8yuVpw6jA3Cc1CpYH1shgk\"," +
                    "\"message\":null,\"stealth\":false}],\"status\":\"broadcasted\",\"inputs\":[{\"publicKeys\":[\"031a661e0ee71dc72abcfe37d069bcf41b88448f6955ac75fdc4c6c9ea44480098\"]," +
                    "\"satoshis\":10100000,\"txid\":\"442c938c6c258f62ab349b5273b00e190af89c96faa3e8eee7941dfb38e85be0\",\"confirmations\":28," +
                    "\"scriptPubKey\":\"76a914d04b76146225d93e206f8c34b461403791b7264288ac\",\"vout\":0,\"address\":\"DQ8TVJGrVPQEYQiGMwnUvP15MdTFP8yBXJ\"," +
                    "\"amount\":0.10100000000000001,\"path\":\"m\\/0\\/0\",\"locked\":false}],\"broadcastedOn\":1555153191,\"message\":null," +
                    "\"id\":\"e72711ed-4ada-4ee3-9d60-deb2ed052f39\",\"version\":3,\"proposalSignaturePubKey\":null,\"payProUrl\":null," +
                    "\"walvarN\":1,\"feeLevel\":\"normal\",\"walvarM\":1,\"feePerKb\":100000,\"createdOn\":1555153186,\"fee\":100000," +
                    "\"network\":\"livenet\",\"coin\":\"xvg\",\"creatorId\":\"8830a04d48c0dcded327806fc903b0f9b5ada46d9a12e0796da40b80e5a2b72c\"," +
                    "\"txid\":\"b92dcf62d353a25a456adaf6c2e2159747f78e7074d2afa3d5c7e659a8a4cee6\",\"walvarId\":\"3547a0ad-2273-4403-ae38-dd0ecdf86d4f\"," +
                    "\"raw\":\"0100000022c1b15c01e05be838fb1d94e7eee8a3fa969cf80a190eb073529b34ab628f256c8c932c44000000006b4830450221008c222a0edf83275e5849d62cb09e57554d96ab643d9ab7af26021f3f9921f8ba02201247b030df5f8f734c679abf1a4e5cb26e3e2824b142a7792f0ed2dc5faf456d0121031a661e0ee71dc72abcfe37d069bcf41b88448f6955ac75fdc4c6c9ea44480098ffffffff0180969800000000001976a91402175bb34e8df3dd2644221a6df2c564c4ff90db88ac00000000\"," +
                    "\"inputPaths\":[\"m\\/0\\/0\"],\"actions\":[{\"type\":\"accept\",\"xpub\":\"ToEA6kpDTfS2mToqS7yg6xzX6kSj8spwrq611jkdGL4fFitpGg8s4uFC5WpQ5839tYMnSbkc59YB23w4sRw2jvpyygBbb9YAcSUqiRuCyicQVz4\"," +
                    "\"signatures\":[\"30450221008c222a0edf83275e5849d62cb09e57554d96ab643d9ab7af26021f3f9921f8ba02201247b030df5f8f734c679abf1a4e5cb26e3e2824b142a7792f0ed2dc5faf456d\"]," +
                    "\"copayerName\":\"{\\\"iv\\\":\\\"eB2o+MVCryZtGlx5DuXY9A==\\\",\\\"v\\\":1,\\\"iter\\\":1,\\\"ks\\\":128,\\\"ts\\\":64,\\\"mode\\\":\\\"ccm\\\",\\\"adata\\\":\\\"\\\",\\\"cipher\\\":\\\"aes\\\",\\\"ct\\\":\\\"1P2zc0PomM9GfWQDkZ4Aleyn4Q==\\\"}\"," +
                    "\"copayerId\":\"8830a04d48c0dcded327806fc903b0f9b5ada46d9a12e0796da40b80e5a2b72c\",\"version\":\"1.0.0\"," +
                    "\"createdOn\":1555153191,\"comment\":null}],\"requiredRejections\":1,\"amount\":10000000,\"timestamp\":1555153186," +
                    "\"proposalSignaturePubKeySig\":null,\"addressType\":\"P2PKH\",\"customData\":null,\"proposalSignature\":\"304402200db38b10cf977e5aa4f142168a3846c90291741cb9b224f1758c73e674bfa59c02204cb4a07a435a71b5b38cb9b89f28a06063afd889737eb488b26ca88f18c5c40b\"," +
                    "\"changeAddress\":{\"beRegistered\":null,\"coin\":\"xvg\",\"hasActivity\":null,\"network\":\"livenet\"," +
                    "\"version\":\"1.0.0\",\"walvarId\":\"3547a0ad-2273-4403-ae38-dd0ecdf86d4f\",\"address\":\"DQsYoSg3h78zMFiifGH3UfneLw7i1CAkmy\"," +
                    "\"publicKeys\":[\"039d9f41b75ab3baaadd8f3e06b7a37797d84cf00bf358d89cb50efb172b101ad5\"],\"isChange\":true," +
                    "\"type\":\"P2PKH\",\"createdOn\":1555153186,\"_id\":\"5cb1c122616c2628658f7d66\",\"path\":\"m\\/1\\/4\"}}"

            var response = TxProposalResponse.decode(responseJson)

                completion(response, null, null)
            }
    }




    open class WalletClientMock: WalletClientInterface {
        override fun createWallet(walletName: String, copayerName: String, m: Int, n: Int, options: WalletOptions?, completion: (Exception?, String?) -> Void) {}
        override fun joinWallet(walletIdentifier: String, completion: (Exception?) -> Void) {}
        override fun openWallet(completion:  (Exception?) -> Void) {}
        override fun scanAddresses(completion:  (Exception?) -> Void) {}
        override fun createAddress(completion:  (Exception?, AddressInfo?, CreateAddressErrorResponse?) -> Void) {}
        override fun getBalance(completion:  (Exception?, WalletBalanceInfo?) -> Void) {}
        override fun  getMainAddresses(options: WalletAddressesOptions?, completion:  (_address : Array<AddressInfo>) -> Void) {}
        override fun  getTxHistory(skip: Int?, limit: Int?, completion:  (_txs : Array<TxHistory>) -> Void) {}
        override fun  getUnspentOutputs(address: String?, completion:  (_addresses : Array<UnspentOutput>) -> Void) {}
        override fun  getSendMaxInfo(completion:  (SendMaxInfo?) -> Void) {}
        override fun  createTxProposal(proposal: TxProposal, completion: TxProposalCompletion) {}
        override fun  publishTxProposal(txp: TxProposalResponse, completion: TxProposalCompletion) {}
        override fun  signTxProposal(txp: TxProposalResponse, completion: TxProposalCompletion) {}
        override fun  broadcastTxProposal(txp: TxProposalResponse, completion: TxProposalCompletion) {}
        override fun  rejectTxProposal(txp: TxProposalResponse, completion:  (Exception?) -> Void) {}
        override fun  deleteTxProposal(txp: TxProposalResponse, completion:  (Exception?) -> Void) {}
        override fun  getTxProposals(completion:  (_response : Array<TxProposalResponse>, Exception?) -> Void) {}
        override fun  resetServiceUrl(baseUrl: String) {}

    }



}