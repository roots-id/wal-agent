print('Start #################################################################');

db = db.getSiblingDB('wal');

db.createCollection('wallets');

db.wallets.insertMany([
    {
        "_id": "wal_t1",
        "seed": "a9421b5af592f3c3682968ffb053f90be2ec2c0c121ce4761ca4bc808a87e6612eb502dab3fb3cf8b27260c698f1ebcc1f7eee2b00da1cebd10c5abdb3f86eb3",
        "dids": [
            {
                "alias": "did_alias_I_1",
                "didIdx": 0,
                "uriCanonical": "did:prism:8353f6663a27d997190bb657dd04e5be782a7bbd8abba24b0dbbad56923d3752",
                "uriLongForm": "did:prism:8353f6663a27d997190bb657dd04e5be782a7bbd8abba24b0dbbad56923d3752:Cr8BCrwBEjsKB21hc3RlcjAQAUouCglzZWNwMjU2azESIQMe-C058Ga11a58SmLrsP2h9Y2OTRfDzKrgnJ4Kf8tALhI8Cghpc3N1aW5nMBACSi4KCXNlY3AyNTZrMRIhA9wpgInT8R-vav0THGKlP0jdMhliYJ05HZmQHH92IvdfEj8KC3Jldm9jYXRpb24wEAVKLgoJc2VjcDI1NmsxEiEDIvjMZV_iBb1DlgJKcrmBOByUUmArRM8gPTFZ0Od5oSI",
                "keyPaths": [
                    {
                        "keyId": "master0",
                        "keyTypeValue": 1,
                        "didIdx": 0,
                        "keyDerivation": 0,
                        "keyIdx": 0,
                        "revoked": false
                    },
                    {
                        "keyId": "issuing0",
                        "keyTypeValue": 2,
                        "didIdx": 0,
                        "keyDerivation": 1,
                        "keyIdx": 0,
                        "revoked": false
                    },
                    {
                        "keyId": "revocation0",
                        "keyTypeValue": 5,
                        "didIdx": 0,
                        "keyDerivation": 4,
                        "keyIdx": 0,
                        "revoked": false
                    }
                ],
                "operationId": [
                    "4412a426db17335002a5cc4011a814ef7fe221d1c9869a8c0a89dbb7d4f41924",
                    "367e993ac2db4fef7e65a49da7279dd9f6ae2a6fba2ad81ce940e9a1e23e3626",
                    "aa01a819d2f748c96ec3e31504f128e3d17fff6bd4457a280a55e0ece7d9e22d"
                ],
                "operationHash": [
                    "8353f6663a27d997190bb657dd04e5be782a7bbd8abba24b0dbbad56923d3752",
                    "cd2e37bfdc6ee0eb5e2936271b46e186e1a9b63970985315203ac80102b1d907",
                    "a87d3ac0f6213829ed2556de363e5ec070ac3948054d483dbe0501ac28f66ef9"
                ]
            },
            {
                "alias": "did_alias_H_1",
                "didIdx": 1,
                "uriCanonical": "did:prism:16039a11d32377259f40cb621b72a8c601e8cc2bbf0abe02831fcdfcd3df9d43",
                "uriLongForm": "did:prism:16039a11d32377259f40cb621b72a8c601e8cc2bbf0abe02831fcdfcd3df9d43:Cj8KPRI7CgdtYXN0ZXIwEAFKLgoJc2VjcDI1NmsxEiECd_wj4ietpCgV-YTUC7Ezd67BH5uB-hHGZgzqPCQyWK4",
                "keyPaths": [
                    {
                        "keyId": "master0",
                        "keyTypeValue": 1,
                        "didIdx": 1,
                        "keyDerivation": 0,
                        "keyIdx": 0,
                        "revoked": false
                    }
                ],
                "operationId": [
                    "0c736d4f0b8f2a050aa1390a451febce566b082c2e335c34a053e4166c3c802e"
                ],
                "operationHash": [
                    "16039a11d32377259f40cb621b72a8c601e8cc2bbf0abe02831fcdfcd3df9d43"
                ]
            },
            {
                "alias": "did_alias_H_2",
                "didIdx": 2,
                "uriCanonical": "did:prism:368e4fb2d31bb8da0a581d1c20ca1d0c9fbf1452b4c4bc3f1b74f97bfdceeb9a",
                "uriLongForm": "did:prism:368e4fb2d31bb8da0a581d1c20ca1d0c9fbf1452b4c4bc3f1b74f97bfdceeb9a:Cj8KPRI7CgdtYXN0ZXIwEAFKLgoJc2VjcDI1NmsxEiEC_S9r2VXMyyk9rWLCARqu4gbdrSUjO-DT9X-QkGSo0X8",
                "keyPaths": [
                    {
                        "keyId": "master0",
                        "keyTypeValue": 1,
                        "didIdx": 2,
                        "keyDerivation": 0,
                        "keyIdx": 0,
                        "revoked": false
                    }
                ],
                "operationId": [
                    "ee2ba6a65f0bbf2a1f8cf36041bf86d2e5b8b10f210da47af34f2a1a691b26ca"
                ],
                "operationHash": [
                    "368e4fb2d31bb8da0a581d1c20ca1d0c9fbf1452b4c4bc3f1b74f97bfdceeb9a"
                ]
            },
            {
                "alias": "did_alias_H_3",
                "didIdx": 3,
                "uriCanonical": "did:prism:65da7642288f0531d4117fe10c8accba59e52adee05a2fd17d5dd76bdbd9deda",
                "uriLongForm": "did:prism:65da7642288f0531d4117fe10c8accba59e52adee05a2fd17d5dd76bdbd9deda:Cj8KPRI7CgdtYXN0ZXIwEAFKLgoJc2VjcDI1NmsxEiECqGjvu8kcwuIoDYu-uAdB2GPUo3NDM4ZGqCFAJ2hMw34",
                "keyPaths": [
                    {
                        "keyId": "master0",
                        "keyTypeValue": 1,
                        "didIdx": 3,
                        "keyDerivation": 0,
                        "keyIdx": 0,
                        "revoked": false
                    }
                ],
                "operationId": [
                    "84488f86cca66ba12a20829375b5e7fe49b5116780f4b64603872e7d801474b3"
                ],
                "operationHash": [
                    "65da7642288f0531d4117fe10c8accba59e52adee05a2fd17d5dd76bdbd9deda"
                ]
            },
            {
                "alias": "did_alias_H_4",
                "didIdx": 4,
                "uriCanonical": "did:prism:da61f8237bbcde4513d445a950b6791a6a6c4a50d22535d955228bf1b8232a20",
                "uriLongForm": "did:prism:da61f8237bbcde4513d445a950b6791a6a6c4a50d22535d955228bf1b8232a20:Cj8KPRI7CgdtYXN0ZXIwEAFKLgoJc2VjcDI1NmsxEiECJ7L4WFi4ptmJIPpGppcrucIdzZeKVoNsZwUUekwuDXY",
                "keyPaths": [
                    {
                        "keyId": "master0",
                        "keyTypeValue": 1,
                        "didIdx": 4,
                        "keyDerivation": 0,
                        "keyIdx": 0,
                        "revoked": false
                    }
                ],
                "operationId": [
                    "18306638af60f4ef12e30124eae2576808fd0a404c87ea3e579bc8989108e830"
                ],
                "operationHash": [
                    "da61f8237bbcde4513d445a950b6791a6a6c4a50d22535d955228bf1b8232a20"
                ]
            }
        ],
        "importedCredentials": [],
        "issuedCredentials": [
            {
                "alias": "credential_2",
                "issuingDidAlias": "did_alias_I_1",
                "claim": {
                    "subjectDid": "did:prism:16039a11d32377259f40cb621b72a8c601e8cc2bbf0abe02831fcdfcd3df9d43:Cj8KPRI7CgdtYXN0ZXIwEAFKLgoJc2VjcDI1NmsxEiECd_wj4ietpCgV-YTUC7Ezd67BH5uB-hHGZgzqPCQyWK4",
                    "content": "{\"content\":\"Credential Content\"}"
                },
                "verifiedCredential": {
                    "encodedSignedCredential": "eyJpZCI6ImRpZDpwcmlzbTo4MzUzZjY2NjNhMjdkOTk3MTkwYmI2NTdkZDA0ZTViZTc4MmE3YmJkOGFiYmEyNGIwZGJiYWQ1NjkyM2QzNzUyIiwia2V5SWQiOiJpc3N1aW5nMCIsImNyZWRlbnRpYWxTdWJqZWN0Ijp7ImNvbnRlbnQiOiJDcmVkZW50aWFsIENvbnRlbnQiLCJpZCI6ImRpZDpwcmlzbToxNjAzOWExMWQzMjM3NzI1OWY0MGNiNjIxYjcyYThjNjAxZThjYzJiYmYwYWJlMDI4MzFmY2RmY2QzZGY5ZDQzOkNqOEtQUkk3Q2dkdFlYTjBaWEl3RUFGS0xnb0pjMlZqY0RJMU5tc3hFaUVDZF93ajRpZXRwQ2dWLVlUVUM3RXpkNjdCSDV1Qi1oSEdaZ3pxUENReVdLNCJ9fQ.MEYCIQDZLNp_VTKS3WTos2euWvAsVBWWTVCJrVAcGm1AckeeMgIhALCmNoAKMlHYNUFk0Np0AwU-1BGis9kF5nWZ5a55xILL",
                    "proof": {
                        "hash": "9871f635444cd5843b2af2c94b658c58ba5f7a2480b6c57d6c8b553da15b2626",
                        "index": 0,
                        "siblings": []
                    }
                },
                "batchId": "aed46da5e56e6e3901f0309dc75898fde1b092b4837cc0a587556540f05916e3",
                "operationId": [
                    "367e993ac2db4fef7e65a49da7279dd9f6ae2a6fba2ad81ce940e9a1e23e3626",
                    "aa01a819d2f748c96ec3e31504f128e3d17fff6bd4457a280a55e0ece7d9e22d"
                ],
                "credentialHash": "9871f635444cd5843b2af2c94b658c58ba5f7a2480b6c57d6c8b553da15b2626",
                "operationHash": "cd2e37bfdc6ee0eb5e2936271b46e186e1a9b63970985315203ac80102b1d907"
            }
        ]
    },
    {
        _id: "issuer_dummy_wallet",
        seed: "027a3db3567abeafe90e9bf626fcb0fdf02a53eece9dcccde86b8ade48421778",
        dids: [
            {
                alias: "issuer_did",
                didIdx: 0,
                uriCanonical: "did:prism:1200d9a3ae40c843700ca4630e9235279f43d384b3b3b2117ec804318c54e9ce",
                uriLongForm: "did:prism:1200d9a3ae40c843700ca4630e9235279f43d384b3b3b2117ec804318c54e9ce:Cr8BCrwBEjsKB21hc3RlcjAQAUouCglzZWNwMjU2azESIQMpmqlFIDgOgaijk5GwhPQKeVJ0P9WLem0cdRqMCzvCHRI8Cghpc3N1aW5nMBACSi4KCXNlY3AyNTZrMRIhAoLIh-fTQMZ9AdXofqK_HhWGwI6Okuw5DloqXI_jnGDXEj8KC3Jldm9jYXRpb24wEAVKLgoJc2VjcDI1NmsxEiEDF6epSenN5YHRBv-NTkL91TdEfWgz-JhkBMhSXjse5WY",
                operationHash: "3d26e01c3bc035e9f590ae5f2bc78c24f8774d18351f9d419c22321ee0635f3e",
                keyPairs: [
                    {
                        keyId: "master0",
                        keyTypeValue: 1,
                        didIdx: 0,
                        keyDerivation: 0,
                        keyIdx: 0,
                        privateKey: "b5ac1d967aac9eb934a684774d20adfd0cb792728e72de8c8e19cfe42103a4bf",
                        publicKey: "04299aa94520380e81a8a39391b084f40a7952743fd58b7a6d1c751a8c0b3bc21d0100aa2da7b437229e2612af5ff93ef7ce954744cb99b267d420aa81223d7dc9",
                        revoked: false
                    },
                    {
                        keyId: "issuing0",
                        keyTypeValue: 2,
                        didIdx: 0,
                        keyDerivation: 1,
                        keyIdx: 0,
                        privateKey: "731a458fd9712ea2818a9944f0060125ad3295293b4db0df688f91bbffbba561",
                        publicKey: "0482c887e7d340c67d01d5e87ea2bf1e1586c08e8e92ec390e5a2a5c8fe39c60d7f1878d146539ab3b89061a53fac0dbb4ce6a77dc9b5af5681effd3b8f16be8cc",
                        revoked: false
                    },
                    {
                        keyId: "revocation0",
                        keyTypeValue: 5,
                        didIdx: 0,
                        keyDerivation: 4,
                        keyIdx: 0,
                        privateKey: "dac9b3a395eeb3a2ce679649c6e725ab9e9af2668e600ffcd210a52d33847b0d",
                        publicKey: "0417a7a949e9cde581d106ff8d4e42fdd537447d6833f8986404c8525e3b1ee566d9ec03b4ce36618bd22b15ab0d5a4270102c4f5ad1097fdae96d627a65fadf11",
                        revoked: false
                    }
                ]
            }
        ],
        importedCredentials: [],
        issuedCredentials: [
            {
                alias: "credential_a",
                issuingDidAlias: "issuer_did",
                claim: {
                    subjectDid: "did:prism:654a4a9113e7625087fd0d3143fcac05ba34013c55e1be12daadd2d5210adc4d:Cj8KPRI7CgdtYXN0ZXIwEAFKLgoJc2VjcDI1NmsxEiEDA7B2nZ_CvcIdkU2ovzBEovGzjwZECMUeHUeNo5_0Jug",
                    content: "{\"name\":\"Charlie\",\"degree\":\"Economics\",\"date\":\"2022-04-23 13:49:57\"}"
                },
                verifiedCredential: {
                    encodedSignedCredential: "eyJpZCI6ImRpZDpwcmlzbToxMjAwZDlhM2FlNDBjODQzNzAwY2E0NjMwZTkyMzUyNzlmNDNkMzg0YjNiM2IyMTE3ZWM4MDQzMThjNTRlOWNlIiwia2V5SWQiOiJpc3N1aW5nMCIsImNyZWRlbnRpYWxTdWJqZWN0Ijp7Im5hbWUiOiJDaGFybGllIiwiZGVncmVlIjoiRWNvbm9taWNzIiwiZGF0ZSI6IjIwMjItMDQtMjMgMTM6NDk6NTciLCJpZCI6ImRpZDpwcmlzbTo2NTRhNGE5MTEzZTc2MjUwODdmZDBkMzE0M2ZjYWMwNWJhMzQwMTNjNTVlMWJlMTJkYWFkZDJkNTIxMGFkYzRkOkNqOEtQUkk3Q2dkdFlYTjBaWEl3RUFGS0xnb0pjMlZqY0RJMU5tc3hFaUVEQTdCMm5aX0N2Y0lka1Uyb3Z6QkVvdkd6andaRUNNVWVIVWVObzVfMEp1ZyJ9fQ.MEUCIQDzhHqtY6rvEFh88nfq28f4y7QttjwpdTC9qDPoBMS_4QIgTGaPY-YCI3Ge8hbK0xLFiFRDrfBkAhTGwCbwwdGRIi8",
                    proof: {
                        hash: "c33c451375c7118b6f58efb257b41b3a2d00f1bb38ae206a9dcade55f6e00b04",
                        index: 0,
                        siblings: []
                    }
                },
                batchId: "82570c55a45259d76dc5fa93f557e182f94a204dc1c594a058aab7e9406baea1",
                operationId: [
                    "367e993ac2db4fef7e65a49da7279dd9f6ae2a6fba2ad81ce940e9a1e23e3626",
                    "aa01a819d2f748c96ec3e31504f128e3d17fff6bd4457a280a55e0ece7d9e22d"
                ],
                credentialHash: "c33c451375c7118b6f58efb257b41b3a2d00f1bb38ae206a9dcade55f6e00b04",
                operationHash: "3d26e01c3bc035e9f590ae5f2bc78c24f8774d18351f9d419c22321ee0635f3e",
                revoked: true
            }
        ],
        blockchainTxLogEntry: [
            {
                txId: "e05eb60e8fadb53eb37cde5ec0e6d5a2722832786533065b2f9cb1b8ff67157c",
                action: "PUBLISH_DID",
                url: "https://explorer.cardano-testnet.iohkdev.io/en/transaction?id=e05eb60e8fadb53eb37cde5ec0e6d5a2722832786533065b2f9cb1b8ff67157c"
            },
            {
                txId: "334a5511c27c03f5652c4e2b68216c4366309639990319c285d55018fb414038",
                action: "ISSUE_CREDENTIAL",
                url: "https://explorer.cardano-testnet.iohkdev.io/en/transaction?id=334a5511c27c03f5652c4e2b68216c4366309639990319c285d55018fb414038"
            },
            {
                txId: "9e91840e7e95fd534a28dec03274ff1944067eb2ea8d2d2566846febd662fca6",
                action: "REVOKE_CREDENTIAL",
                url: "https://explorer.cardano-testnet.iohkdev.io/en/transaction?id=9e91840e7e95fd534a28dec03274ff1944067eb2ea8d2d2566846febd662fca6"
            }
        ]
    },
    {
        _id: "holder_dummy_wallet",
        seed: "2d0c04c8c5b285103b72677528a15bcf880161820d571107572de82c296b2f54",
        dids: [
            {
                alias: "holder_did",
                didIdx: 0,
                uriCanonical: "did:prism:654a4a9113e7625087fd0d3143fcac05ba34013c55e1be12daadd2d5210adc4d",
                uriLongForm: "did:prism:654a4a9113e7625087fd0d3143fcac05ba34013c55e1be12daadd2d5210adc4d:Cj8KPRI7CgdtYXN0ZXIwEAFKLgoJc2VjcDI1NmsxEiEDA7B2nZ_CvcIdkU2ovzBEovGzjwZECMUeHUeNo5_0Jug",
                operationHash: "",
                keyPairs: [
                    {
                        keyId: "master0",
                        keyTypeValue: 1,
                        didIdx: 0,
                        keyDerivation: 0,
                        keyIdx: 0,
                        privateKey: "9c2a64d860cb86ce0af23787fccd2ad12a73d5e758c706d8567de49dec2ec029",
                        publicKey: "0403b0769d9fc2bdc21d914da8bf3044a2f1b38f064408c51e1d478da39ff426e884c34858bcfa2afbd3cc4e4b1a8d3fc848b74f92360e91729aaf8d77d8207963",
                        revoked: false
                    }
                ]
            }
        ],
        importedCredentials: [
            {
                alias: "credential_a",
                verifiedCredential: {
                    encodedSignedCredential: "eyJpZCI6ImRpZDpwcmlzbToxMjAwZDlhM2FlNDBjODQzNzAwY2E0NjMwZTkyMzUyNzlmNDNkMzg0YjNiM2IyMTE3ZWM4MDQzMThjNTRlOWNlIiwia2V5SWQiOiJpc3N1aW5nMCIsImNyZWRlbnRpYWxTdWJqZWN0Ijp7Im5hbWUiOiJDaGFybGllIiwiZGVncmVlIjoiRWNvbm9taWNzIiwiZGF0ZSI6IjIwMjItMDQtMjMgMTM6NDk6NTciLCJpZCI6ImRpZDpwcmlzbTo2NTRhNGE5MTEzZTc2MjUwODdmZDBkMzE0M2ZjYWMwNWJhMzQwMTNjNTVlMWJlMTJkYWFkZDJkNTIxMGFkYzRkOkNqOEtQUkk3Q2dkdFlYTjBaWEl3RUFGS0xnb0pjMlZqY0RJMU5tc3hFaUVEQTdCMm5aX0N2Y0lka1Uyb3Z6QkVvdkd6andaRUNNVWVIVWVObzVfMEp1ZyJ9fQ.MEUCIQDzhHqtY6rvEFh88nfq28f4y7QttjwpdTC9qDPoBMS_4QIgTGaPY-YCI3Ge8hbK0xLFiFRDrfBkAhTGwCbwwdGRIi8",
                    proof: {
                        hash: "c33c451375c7118b6f58efb257b41b3a2d00f1bb38ae206a9dcade55f6e00b04",
                        index: 0,
                        siblings: []
                    }
                }
            }
        ],
        issuedCredentials: [],
        blockchainTxLogEntry: []
    }
]);

print('End #################################################################');
