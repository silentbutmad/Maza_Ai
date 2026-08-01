# API Keys Setup Guide

This document explains what API keys are required for this project and how to obtain them.

## Required API Keys

### 1. Stability AI API Key (for Image Generation)

**Status:** ✅ Already configured  
**Current Value:** `sk-wRhTqEIDKkcuzASUhz6AgemeygxjI80sn2Qrg9l9r2Rw9r3d`

**Purpose:** Used for AI image generation features via the Stability AI API.

**How to get your own key:**
1. Visit [Stability AI Platform](https://platform.stability.ai/)
2. Sign up or log in to your account
3. Navigate to your account settings or API keys section
4. Generate a new API key
5. Copy the key (starts with `sk-`)
6. Replace the value in `application.properties` under `spring.ai.stabilityai.api-key`

**Pricing:** Check [Stability AI Pricing](https://platform.stability.ai/pricing) for current rates.

---

### 2. Google Custom Search API Key (for Web Search)

**Status:** ⚠️ **MISSING - REQUIRES SETUP**  
**Current Value:** Empty (`""`)  
**Search Engine ID (CX):** `7576d45c732874017` (already configured)

**Purpose:** Used for web search functionality to fetch real-time information.

**How to obtain:**

#### Step 1: Create a Google Cloud Project
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Click "Select a project" → "New Project"
3. Enter project name (e.g., "Maza AI Search")
4. Click "Create"

#### Step 2: Enable Custom Search API
1. In your project, go to "APIs & Services" → "Library"
2. Search for "Custom Search API"
3. Click on it and click "Enable"

#### Step 3: Create API Key
1. Go to "APIs & Services" → "Credentials"
2. Click "Create Credentials" → "API Key"
3. Copy the generated API key
4. (Recommended) Click "Restrict Key" and:
   - Select "Custom Search API" under "API restrictions"
   - This limits the key to only be used for search

#### Step 4: Get Search Engine ID (if needed)
The CX value `7576d45c732874017` is already configured. If you need to create your own:
1. Visit [Google Custom Search Engine](https://programmablesearchengine.google.com/)
2. Click "Add" to create a new search engine
3. Configure it to search the entire web or specific sites
4. Copy the Search Engine ID (CX)

#### Step 5: Update Configuration
Add your API key to `application.properties`:
```properties
google.search.api.key=YOUR_API_KEY_HERE
```

**Pricing:** 
- First 100 queries/day: FREE
- After that: $5 per 1000 queries
- See [Google Cloud Pricing](https://cloud.google.com/custom-search/pricing)

---

### 3. Google Vertex AI Gemini (for AI Chat)

**Status:** ✅ Already configured  
**Project ID:** `gen-lang-client-0002860574`  
**Location:** `asia-southeast1`

**Purpose:** Used for AI chat capabilities via Google's Gemini model.

**How to set up authentication:**

#### Option A: Service Account (Recommended for Production)
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Select your project
3. Go to "IAM & Admin" → "Service Accounts"
4. Click "Create Service Account"
5. Enter name and description
6. Grant role: "Vertex AI User"
7. Click "Done"
8. Click on the created service account
9. Go to "Keys" tab → "Add Key" → "Create new key"
10. Select "JSON" format
11. Download the key file
12. Set environment variable:
   ```bash
   export GOOGLE_APPLICATION_CREDENTIALS="/path/to/your/keyfile.json"
   ```

#### Option B: gcloud CLI (for Development)
1. Install [Google Cloud SDK](https://cloud.google.com/sdk/docs/install)
2. Run: `gcloud auth application-default login`
3. This will authenticate locally

**Note:** The Vertex AI API must be enabled in your Google Cloud project.

**Pricing:** Check [Vertex AI Pricing](https://cloud.google.com/vertex-ai/pricing)

---

## Configuration Files

### application.properties
All API keys are configured in `src/main/resources/application.properties`:

```properties
# Vertex AI Gemini Configuration
spring.ai.vertex.ai.gemini.project-id=gen-lang-client-0002860574
spring.ai.vertex.ai.gemini.location=asia-southeast1

# Stability AI Configuration
spring.ai.stabilityai.api-key=sk-wRhTqEIDKkcuzASUhz6AgemeygxjI80sn2Qrg9l9r2Rw9r3d

# Google Custom Search Configuration
google.search.api.key=YOUR_GOOGLE_SEARCH_API_KEY
google.search.cx=7576d45c732874017

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/maza_ai
spring.datasource.username=root
spring.datasource.password=system
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Security Best Practices

1. **Never commit API keys to version control**
   - Add `application.properties` to `.gitignore`
   - Use environment variables or external configuration for production

2. **Use environment variables in production:**
   ```bash
   export STABILITY_AI_API_KEY=your_key
   export GOOGLE_SEARCH_API_KEY=your_key
   export GOOGLE_APPLICATION_CREDENTIALS=/path/to/keyfile.json
   ```

3. **Restrict API keys:**
   - Limit Google API keys to specific APIs
   - Set up usage quotas and alerts
   - Rotate keys regularly

4. **Monitor usage:**
   - Set up billing alerts
   - Monitor API usage dashboards
   - Watch for unusual activity

## Quick Start

1. **Get Google Custom Search API Key** (currently missing - REQUIRED)
2. Update `application.properties` with your Google Search API key
3. Ensure MySQL database is running
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Troubleshooting

### Error: "No qualifying bean of type 'ImageModel'"
- **Solution:** Ensure `spring.ai.stabilityai.api-key` is set in `application.properties`

### Error: "Google Search API returning 403"
- **Solution:** Check if API key is valid and Custom Search API is enabled

### Error: "UNAUTHENTICATED: Request had invalid authentication credentials"
- **Cause:** Service account doesn't have "Vertex AI User" role
- **Solution:** 
  1. Go to https://console.cloud.google.com/iam-admin/serviceaccounts?project=gen-lang-client-0002860574
  2. Find service account: `mad-173@gen-lang-client-0002860574.iam.gserviceaccount.com`
  3. Click "Edit" → "Add another role"
  4. Search for "Vertex AI User" and add it
  5. Wait 1-2 minutes for permissions to propagate
  6. Restart the application

### Error: "Vertex AI authentication failed"
- **Solution:** Ensure `GOOGLE_APPLICATION_CREDENTIALS` environment variable is set correctly

## Support

For issues with:
- **Stability AI:** https://platform.stability.ai/docs
- **Google Custom Search:** https://developers.google.com/custom-search/v1/overview
- **Google Vertex AI:** https://cloud.google.com/vertex-ai/docs