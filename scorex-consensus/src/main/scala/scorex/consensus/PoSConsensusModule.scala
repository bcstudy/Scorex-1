package scorex.consensus

import scorex.account.Account
import scorex.block.Block
import scorex.transaction.{BalanceSheet, TransactionModule}

/**
 * Data and functions related to a Proof-of-Stake consensus algo
 */
trait PoSConsensusModule[ConsensusBlockData] extends ConsensusModule[ConsensusBlockData] {
  
  def generatingBalance[TransactionalBlockData](address: String)
                                               (implicit transactionModule: TransactionModule[TransactionalBlockData]): Long = {
    transactionModule.blockStorage.state.asInstanceOf[BalanceSheet]
      .balanceWithConfirmations(address, generatingBalanceDepth)
  }

  def generatingBalance[TransactionalBlockData](account: Account)
                                               (implicit transactionModule: TransactionModule[TransactionalBlockData]): Long =
    generatingBalance(account.address)

  val generatingBalanceDepth: Int

  override def generators(block: Block): Seq[Account]
}
