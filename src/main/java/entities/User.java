package entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserOrder> orders;

    public User(Long id, String userName, String password, String email, List<UserOrder> orders) {
        this.id = id;
        this.userName = userName;
        setPassword(password);
        this.email = email;
        this.orders = orders;
    }

    public void setPassword(String password) {
        this.password = generateHashedPassword(password);
    }

    public boolean isPasswordCorrect(String password) {
        String passwordForCheck = generateHashedPassword(password);
        return getPassword().equals(passwordForCheck);
    }

    private String generateHashedPassword(String password) {
        String salt = userName;
        char[] passwordChars = password.toCharArray();
        byte[] saltByte = salt.getBytes();
        byte[] hashedBytes = null;

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

            PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordChars, saltByte, 1000, 100);
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
            hashedBytes = secretKey.getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return Hex.encodeHexString(hashedBytes);
    }
}