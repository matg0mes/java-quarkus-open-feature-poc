## O que é Open Feature?

Open Feature é uma especificação que inverte dependências de providers de **Feature Flags** para que eles sejam utilizados por uma interface "plugável". Assim conseguimos resolver problemas de **[[Vendors]] Lock-In**. Assim como o [[Open Telemetry]] ele conta com um [marketplace](https://openfeature.dev/ecosystem) com suporte para vários providers e linguagens de programação.

## Providers de Open Feature

- [Unleash](https://www.getunleash.io/)
- [Flipt](https://www.flipt.io/)

# POC do Open Feature

Foi realizado uma POC (Proof of Concept) entendendo como implementar o Open Feature. Nele foi utilizado o provider [Unleash](https://www.getunleash.io/) junto de um client com Java Quarkus.
## Quick Start

Utilize o comando **docker-compose up** na pasta raiz do projeto. Na porta http://localhost:4242 você poderá acessar a plataforma do Unleash.

<div  align="center">
<img src="https://i.imgur.com/lF1YcY6.png"  />
</div>

>  Acesso ao Unleash a senha padrão de acesso é: Login - **admin** | Senha - **unleash4all**

Uma vez na plataforma, crie uma **chave de api** e coloque-a nas suas variáveis de ambiente com o nome **UNLEASH_API_KEY**:

<div  align="center">
<img src="https://i.imgur.com/AD4L3Wk.png"  />
</div>

Logo após, crie uma Feature Flag. Estamos usando o nome **is-a-potato** para essa POC:

<div  align="center">
<img src="https://i.imgur.com/n7Bl3BB.png" />
</div>

Uma vez criada a flag a aplicação precisa chamar a classe **FeatureFlagConfig** passando o nome da flag como parâmetro em método **getBooleanValue**. Snippet da classe:

```java
@ApplicationScoped  
public class FeatureUseCase implements IFeatureUseCase {  
    private static final String FLAG_NAME_POTATO = "is-a-potato";  
  
    @Inject  
    FeatureFlagConfig featureFlagConfig;  
  
    public String execute() {  
        try {  
            Client client = featureFlagConfig.getUnleashClient();  
            Boolean featureEnabled = client.getBooleanValue(FLAG_NAME_POTATO, false);  
  
            return "Potato is: " + featureEnabled;  
        } catch (URISyntaxException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
}
```

### Constraints

Constraints são campos cujo é possível parametrizar valores que serão permitidos para aquela feature ser **verdadeira**.

<div  align="center">
<img src="https://i.imgur.com/eWjAj4j.png"  />
</div>

> Não somente a feature está habilitada como o valor **potato-type** precisa ser "gratinada" ou "frita" para devolver **true** 

