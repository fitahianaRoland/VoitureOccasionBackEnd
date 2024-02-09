package monpackage.ventevoiture.Repository;

import monpackage.ventevoiture.model.ProprietaireVoitureView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProprietaireVoitureRepository extends JpaRepository<ProprietaireVoitureView, Integer> {
    List<ProprietaireVoitureView> findById(int id);

    @Query(value = "\n" +
            "SELECT * FROM voiture_annonce \n" +
            "WHERE LOWER(marque) LIKE LOWER(CONCAT('%',:Marque,'%')) \n" +
            "    AND LOWER(modele) LIKE LOWER(CONCAT('%',:Modele,'%'))\n" +
            "    AND LOWER(categorie) LIKE LOWER(CONCAT('%',:Categorie,'%'))\n" +
            "    AND LOWER(energie) LIKE LOWER(CONCAT('%',:Energie,'%'))\n" +
            "    AND LOWER(type_vitesse) LIKE LOWER(CONCAT('%',:Vitesse,'%'))\n" +
            "    AND prix BETWEEN :prixMin AND :prixMax\n" +
            "    AND EXTRACT(YEAR FROM annee_circulation) BETWEEN :dateDebut AND :dateFin", nativeQuery = true)

    List<ProprietaireVoitureView> findVoituresByCriteria(@Param("Marque") String Marque,
                                                         @Param("Modele") String Modele,
                                                         @Param("Energie") String Energie,
                                                         @Param("prixMin") double prixMin,
                                                         @Param("prixMax") double prixMax,
                                                         @Param("Categorie") String Categorie,
                                                         @Param("Vitesse") String Vitesse,
                                                         @Param("dateDebut") int dateDebut,
                                                         @Param("dateFin") double dateFin);

    @Query(value = "SELECT * FROM voiture_annonce  \n" +
            "WHERE LOWER(marque) LIKE LOWER(CONCAT('%', :mots, '%'))\n" +
            "OR LOWER(modele) LIKE LOWER(CONCAT('%', :mots, '%')) \n" +
            "OR LOWER(categorie) LIKE LOWER(CONCAT('%', :mots, '%'))\n" +
            "OR LOWER(energie) LIKE LOWER(CONCAT('%', :mots, '%'));", nativeQuery = true)
    List<ProprietaireVoitureView> findVoituresByMots(@Param("mots") String mots);


    long count();

    @Query(value = "SELECT COUNT(*) FROM voiture_annonce", nativeQuery = true)
    int countTotalVoitures();

    @Query(value = "SELECT * FROM voiture_annonce WHERE etat_annonce = 1",nativeQuery = true)
    List<ProprietaireVoitureView> findVoitureByEtat();

    @Query(value = "select min(prix) from voiture_view", nativeQuery = true)
    int min_prix();

    @Query(value = "select max(prix) from voiture_view", nativeQuery = true)
    int max_prix();

    @Query(value = "\n" +
            "select min(EXTRACT(YEAR FROM annee_circulation::DATE)) from voiture_view ",nativeQuery = true)
    int minAnneCirculation();

    @Query(value = "\n" +
            "select max(EXTRACT(YEAR FROM annee_circulation::DATE)) from voiture_view ",nativeQuery = true)
    int maxAnneCirculation();

}
