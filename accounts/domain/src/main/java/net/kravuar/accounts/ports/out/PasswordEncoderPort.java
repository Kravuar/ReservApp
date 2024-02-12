package net.kravuar.accounts.ports.out;

public interface PasswordEncoderPort {
    /**
     * Encode password.
     *
     * @param password password to encode
     * @return the encoded password
     */
    String encode(String password);
}
