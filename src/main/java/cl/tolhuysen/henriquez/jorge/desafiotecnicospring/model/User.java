package cl.tolhuysen.henriquez.jorge.desafiotecnicospring.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="USERS")
public class User
    implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        @Column(name="USER_ID")
        private long userId;

        @Column(name="USER_EMAIL")
        private String userEmail;

        @Column(name="USER_FIRSTNAME")
        private String userFirstname;

        @Column(name="USER_LASTNAME")
        private String userLastname;

        @Column(name="USER_PASSWORD")
        private String userPassword;

        @Column(name="USER_ISBORROWER")
        private String userIsborrower;

        @Column(name="USER_ISLENDER")
        private String userIslender;

        //bi-directional one-to-one association to BorrowerData
        @OneToOne(mappedBy="user")
        private BorrowerData borrowerData;

        //bi-directional one-to-one association to LenderData
        @OneToOne(mappedBy="user")
        private LenderData lenderData;

	    public User() {
        }

    public User(String userFirstname, String userLastname, String userEmail, String userPassword, String userIslender, String userIsborrower) {
        this.userFirstname = userFirstname;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userIslender = userIslender;
        this.userIsborrower = userIsborrower;
    }

    public long getUserId() {
            return this.userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserEmail() {
            return this.userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserIsborrower() {
            return this.userIsborrower;
        }

        public void setUserIsborrower(String userIsborrower) {
            this.userIsborrower = userIsborrower;
        }

        public String getUserIslender() {
            return this.userIslender;
        }

        public void setUserIslender(String userIslender) {
            this.userIslender = userIslender;
        }

        public String getUserFirstname() {
            return this.userFirstname;
        }

        public void setUserFirstname(String userFirstname) {
            this.userFirstname = userFirstname;
        }

        public String getUserLastname() {
            return this.userLastname;
        }

        public void setUserLastname(String userLastname) {
            this.userLastname = userLastname;
        }

        public String getUserPassword() {
            return this.userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public BorrowerData getBorrowerData() {
            return this.borrowerData;
        }

        public void setBorrowerData(BorrowerData borrowerData) {
            this.borrowerData = borrowerData;
        }

        public LenderData getLenderData() {
            return this.lenderData;
        }

        public void setLenderData(LenderData lenderData) {
            this.lenderData = lenderData;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return userEmail.equals(user.userEmail);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userEmail);
        }
}
