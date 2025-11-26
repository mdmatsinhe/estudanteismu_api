package siga.artsoft.api.user;

public record DadosDetalheUser(
        long id, String password, int loginCount
) {
    public DadosDetalheUser(User user){
        this(user.getId(),user.getPassword(),user.getLoginCount());
    }
}
