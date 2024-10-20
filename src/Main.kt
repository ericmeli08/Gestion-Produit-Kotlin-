import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Scanner

open class Produit(
    var nom: String,
    var prixUnitaire: Double,
    var quantiteStock: Int
) {
    open fun afficherDetails() {
        println("\t-- Nom: $nom, Prix Unitaire: $prixUnitaire, Quantité en Stock: $quantiteStock")
    }
}


class ProduitAlimentaire(
    nom: String,
    prixUnitaire: Double,
    quantiteStock: Int,
    var datePeremption: LocalDate
) : Produit(nom, prixUnitaire, quantiteStock) {

    fun estPerime(): Boolean {
        return LocalDate.now().isAfter(datePeremption)
    }

    override fun afficherDetails() {
        super.afficherDetails()
        println("\t   Date de Péremption: $datePeremption")
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val inventaire = mutableListOf<Produit>()
    var array  = ArrayList<Int>()

    inventaire.add(Produit("Biscuit", 25.0, 100))
    inventaire.add(ProduitAlimentaire("Broli", 1500.0, 50, LocalDate.of(2024, 10, 15)))
    inventaire.add(Produit("Pain", 150.0, 30))
    inventaire.add(ProduitAlimentaire("Tomate", 100.0, 20, LocalDate.of(2023, 10, 1)))
    inventaire.add(Produit("Savon", 300.0, 10))

    var continuer = true

    while (continuer) {
        println("\nMenu:")
        println("1. Ajouter un produit")
        println("2. Supprimer un produit")
        println("3. Afficher la liste des produits")
        println("4. Vérifier si un produit alimentaire est périmé")
        println("5. Quitter")
        print("Choisissez une option: ")

        when (scanner.nextInt()) {
            1 -> {
                println("Entrez le nom du produit:")
                val nom = readln()
                println("Entrez le prix unitaire:")
                val prix = scanner.nextDouble()
                println("Entrez la quantité en stock:")
                val quantite = scanner.nextInt()

                println("Est-ce un produit alimentaire? (y/n)")
                if (scanner.next() == "y") {
                    try {
                        println("Entrez la date de péremption (AAAA-MM-JJ):")
                        val datePeremption = LocalDate.parse(scanner.next())
                        inventaire.add(ProduitAlimentaire(nom, prix, quantite, datePeremption))
                    }catch (e : DateTimeParseException){
                        println("Entrez invalide !\nVotre produit ne sera pas alimentaire. ")
                        inventaire.add(Produit(nom, prix, quantite))
                    }
                } else {
                    inventaire.add(Produit(nom, prix, quantite))
                }
                println("Produit ajouté.")
            }
            2 -> {
                println("Entrez le nom du produit à supprimer:")
                val nom = readln()
                inventaire.removeIf { it.nom == nom }
                println("Produit supprimé.")
            }
            3 -> {
                println("Liste des produits:")
                inventaire.forEach { it.afficherDetails() }
            }
            4 -> {
                println("Entrez le nom du produit alimentaire a verifier:")
                val nom = readln()
                val produit =  inventaire.find { it.nom == nom && it is ProduitAlimentaire } as? ProduitAlimentaire
                if (produit != null) {
                    println("${produit.nom} est perime: ${produit.estPerime()}")
                } else {
                    println("Produit non trouve ou ce n'est pas un produit alimentaire.")
                }
            }
            5 -> {
                continuer = false
                println("Au revoir!")
            }
            else -> {
                println("Option invalide. Veuillez réessayer.")
            }
        }
    }
}
