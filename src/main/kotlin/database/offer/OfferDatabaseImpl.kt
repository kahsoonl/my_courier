package org.example.database.offer

import org.example.data.model.OfferModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.Statement


class OfferDatabaseImpl(
    private val dbConnection: Connection
) : OfferDatabase {
    override fun createOfferTable() {
        val statement: Statement = dbConnection.createStatement()
        statement.execute("CREATE TABLE IF NOT EXISTS offer (" +
                "offer_id TEXT," +
                "offer_min_dist INTEGER," +
                "offer_max_dist INTEGER," +
                "offer_min_weight INTEGER," +
                "offer_max_weight INTEGER," +
                "offer_disc_percent INTEGER" +
                ")"
        )
    }

    override fun insertOffer(offerModel: OfferModel): Boolean {
        val sqlStatement = "INSERT INTO offer (offer_id," +
                " offer_min_dist," +
                " offer_max_dist," +
                " offer_min_weight," +
                " offer_max_weight," +
                " offer_disc_percent" +
                ") VALUES (" +
                "'${offerModel.offerId}'," +
                "'${offerModel.offerMinDistance}'," +
                "'${offerModel.offerMaxDistance}'," +
                "'${offerModel.offerMinWeight}'," +
                "'${offerModel.offerMaxWeight}'," +
                "'${offerModel.offerDiscountPercentage}'" +
                ")"
        val preparedStatement: PreparedStatement = dbConnection.prepareStatement(sqlStatement)
        return preparedStatement.executeUpdate() > 0
    }

    override fun deleteOffer(offerId: String): Boolean {
        val sqlStatement = "DELETE FROM offer WHERE offer_id = '$offerId'"
        val preparedStatement: PreparedStatement = dbConnection.prepareStatement(sqlStatement)
        return preparedStatement.executeUpdate() > 0
    }

    override fun getOffer(): List<OfferModel> {
        val statement: Statement = dbConnection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM offer")
        val offerList : MutableList<OfferModel> = mutableListOf()

        while (resultSet.next()) {
            offerList.add(
                OfferModel(
                    offerId = resultSet.getString("offer_id"),
                    offerMinDistance = resultSet.getInt("offer_min_dist"),
                    offerMaxDistance = resultSet.getInt("offer_max_dist"),
                    offerMinWeight = resultSet.getInt("offer_min_weight"),
                    offerMaxWeight = resultSet.getInt("offer_max_weight"),
                    offerDiscountPercentage = resultSet.getInt("offer_disc_percent"),
                )
            )
        }

        return offerList
    }
}