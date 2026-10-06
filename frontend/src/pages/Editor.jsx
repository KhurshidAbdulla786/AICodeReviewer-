import { useState, useRef, useCallback } from 'react';
import Layout from '../components/Layout';
import apiClient from '../api/axios';
import Editor from '@monaco-editor/react';
import toast from 'react-hot-toast';
import { Play, Save, Upload, FileCode, Copy, Check } from 'lucide-react';

const LANGUAGES = [
  { id: 'java', name: 'Java', extension: '.java' },
  { id: 'python', name: 'Python', extension: '.py' },
  { id: 'javascript', name: 'JavaScript', extension: '.js' },
  { id: 'typescript', name: 'TypeScript', extension: '.ts' },
  { id: 'cpp', name: 'C++', extension: '.cpp' },
  { id: 'csharp', name: 'C#', extension: '.cs' },
  { id: 'go', name: 'Go', extension: '.go' },
];

const DEFAULT_CODE = {
  java: 'public class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println("Hello, AI Code Reviewer!");\n    }\n}',
  python: 'def hello():\n    print("Hello, AI Code Reviewer!")',
  javascript: 'function hello() {\n    console.log("Hello, AI Code Reviewer!");\n}',
  typescript: 'function hello(): void {\n    console.log("Hello, AI Code Reviewer!");\n}',
  cpp: '#include <iostream>\nint main() {\n    std::cout << "Hello, AI Code Reviewer!" << std::endl;\n    return 0;\n}',
  csharp: 'using System;\nclass Program {\n    static void Main() {\n        Console.WriteLine("Hello, AI Code Reviewer!");\n    }\n}',
  go: 'package main\nimport "fmt"\nfunc main() {\n    fmt.Println("Hello, AI Code Reviewer!")\n}',
};

export default function CodeEditor() {
  const [code, setCode] = useState(DEFAULT_CODE.java);
  const [language, setLanguage] = useState('java');
  const [filename, setFilename] = useState('Main.java');
  const [reviewing, setReviewing] = useState(false);
  const [reviewResult, setReviewResult] = useState(null);
  const [copied, setCopied] = useState(false);
  const fileInputRef = useRef(null);

  const handleLanguageChange = (lang) => {
    const selectedLang = LANGUAGES.find(l => l.id === lang);
    setLanguage(lang);
    setCode(DEFAULT_CODE[lang]);
    setFilename(`Main${selectedLang.extension}`);
    setReviewResult(null);
  };

  const handleFileUpload = (e) => {
    const file = e.target.files?.[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = (event) => {
      setCode(event.target.result);
      setFilename(file.name);
      // Detect language from extension
      const ext = '.' + file.name.split('.').pop();
      const lang = LANGUAGES.find(l => l.extension === ext);
      if (lang) setLanguage(lang.id);
    };
    reader.readAsText(file);
  };

  const handleReview = async () => {
    setReviewing(true);
    setReviewResult(null);
    try {
      const response = await apiClient.post('/projects/review', {
        projectId: 0, // will be replaced with actual project ID
        code,
        filename,
        language: language.toUpperCase(),
      });
      setReviewResult(response.data.data);
      toast.success('Review completed!');
    } catch (err) {
      toast.error('Review failed. Please try again.');
    } finally {
      setReviewing(false);
    }
  };

  const copyCode = async () => {
    await navigator.clipboard.writeText(code);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <Layout>
      <div className="h-full flex flex-col">
        {/* Toolbar */}
        <div className="flex items-center gap-3 mb-4 flex-wrap">
          <select
            value={language}
            onChange={(e) => handleLanguageChange(e.target.value)}
            className="input-field w-auto"
          >
            {LANGUAGES.map((lang) => (
              <option key={lang.id} value={lang.id}>{lang.name}</option>
            ))}
          </select>

          <input
            type="text"
            value={filename}
            onChange={(e) => setFilename(e.target.value)}
            className="input-field w-auto flex-1 min-w-[150px]"
            placeholder="filename"
          />

          <button onClick={() => fileInputRef.current?.click()} className="btn-secondary text-sm">
            <Upload className="w-4 h-4 mr-1 inline" />
            Upload
          </button>
          <input
            ref={fileInputRef}
            type="file"
            accept=".java,.py,.js,.ts,.cpp,.cs,.go,.zip"
            className="hidden"
            onChange={handleFileUpload}
          />

          <button onClick={copyCode} className="btn-secondary text-sm">
            {copied ? <Check className="w-4 h-4 mr-1 inline" /> : <Copy className="w-4 h-4 mr-1 inline" />}
            {copied ? 'Copied' : 'Copy'}
          </button>

          <button
            onClick={handleReview}
            disabled={reviewing || !code.trim()}
            className="btn-primary text-sm ml-auto"
          >
            <Play className="w-4 h-4 mr-1 inline" />
            {reviewing ? 'Reviewing...' : 'Review Code'}
          </button>
        </div>

        {/* Editor and Results */}
        <div className="flex-1 flex gap-4 min-h-0">
          <div className="flex-1 rounded-lg overflow-hidden border border-gray-800">
            <Editor
              height="100%"
              language={language}
              theme="vs-dark"
              value={code}
              onChange={(value) => setCode(value || '')}
              options={{
                minimap: { enabled: false },
                fontSize: 14,
                lineNumbers: 'on',
                automaticLayout: true,
                scrollBeyondLastLine: false,
                wordWrap: 'on',
                tabSize: 4,
                renderWhitespace: 'selection',
              }}
            />
          </div>

          {/* Review Results Panel */}
          {reviewResult && (
            <div className="w-96 bg-gray-900 border border-gray-800 rounded-lg p-4 overflow-y-auto">
              <h3 className="text-lg font-semibold text-white mb-4">Review Results</h3>
              
              {/* Score */}
              <div className="text-center mb-6">
                <div className={`inline-flex items-center justify-center w-20 h-20 rounded-full text-2xl font-bold mb-2 ${
                  reviewResult.overallScore >= 80 ? 'bg-green-500/20 text-green-400' :
                  reviewResult.overallScore >= 60 ? 'bg-yellow-500/20 text-yellow-400' :
                  'bg-red-500/20 text-red-400'
                }`}>
                  {reviewResult.overallScore}
                </div>
                <p className="text-sm text-gray-400">Overall Score</p>
              </div>

              {/* Bug Detections */}
              {reviewResult.bugs && reviewResult.bugs.length > 0 && (
                <div className="mb-4">
                  <h4 className="text-sm font-semibold text-red-400 mb-2">Bugs Detected</h4>
                  {reviewResult.bugs.map((bug, i) => (
                    <div key={i} className="bg-red-500/10 border border-red-500/20 rounded-lg p-3 mb-2">
                      <p className="text-sm font-medium text-red-300">{bug.bugType}</p>
                      {bug.lineNumber && <p className="text-xs text-gray-500">Line {bug.lineNumber}</p>}
                      <p className="text-xs text-gray-400 mt-1">{bug.description}</p>
                    </div>
                  ))}
                </div>
              )}

              {/* Scores Grid */}
              <div className="grid grid-cols-2 gap-2 mb-4">
                {[
                  { label: 'Readability', value: reviewResult.readabilityScore },
                  { label: 'Naming', value: reviewResult.namingScore },
                  { label: 'Style', value: reviewResult.styleScore },
                  { label: 'SOLID', value: reviewResult.solidScore },
                  { label: 'OOP', value: reviewResult.oopScore },
                  { label: 'Security', value: reviewResult.securityScore },
                  { label: 'Performance', value: reviewResult.performanceScore },
                  { label: 'Maintainability', value: reviewResult.maintainabilityScore },
                ].map((item) => (
                  <div key={item.label} className="bg-gray-800/50 rounded-lg p-2 text-center">
                    <p className="text-xs text-gray-500">{item.label}</p>
                    <p className={`text-sm font-bold ${
                      item.value >= 80 ? 'text-green-400' :
                      item.value >= 60 ? 'text-yellow-400' : 'text-red-400'
                    }`}>{item.value || 'N/A'}</p>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </Layout>
  );
}