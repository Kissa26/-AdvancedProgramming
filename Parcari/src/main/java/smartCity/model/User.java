package smartCity.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Table(name = "users")
@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class User {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("username")
    @Column(name="username")
    private String username;

    @JsonProperty("parola")
    @Column(name="parola")
    private String parola;

    @JsonProperty("email")
    @Column(name="email")
    private String email;

    @JsonProperty("logat")
    @Column(name="logat")
    private boolean logat;

    public User(){};

    public User(String username, String parola, String email) {
        this.username = username;
        this.parola = parola;
        this.email = email;
    }

    public User(String username, String password) {
        this.username = username;
        this.parola = password;
    }
}
