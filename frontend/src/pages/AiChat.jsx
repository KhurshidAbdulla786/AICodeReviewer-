import React, { useState } from 'react';
import Layout from '../components/Layout';
import { Send, Bot, User as UserIcon, Code, Sparkles, MessageSquare } from 'lucide-react';
import MarkdownMessage from '../components/MarkdownMessage';

export default function AiChat() {
  const [messages, setMessages] = useState([
    { role: 'assistant', content: 'Hello! I am your AI Code Assistant. How can I help you analyze, refactor, or explain your code today?' }
  ]);
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSend = (e) => {
    e.preventDefault();
    if (!input.trim() || loading) return;

    const userMsg = input.trim();
    setInput('');
    setMessages(prev => [...prev, { role: 'user', content: userMsg }]);
    setLoading(true);

    setTimeout(() => {
      setMessages(prev => [...prev, {
        role: 'assistant',
        content: `I received your query about: "${userMsg}". As an AI code reviewer assistant integrated with Spring AI, I am ready to help you optimize and debug your code!`
      }]);
      setLoading(false);
    }, 1000);
  };

  return (
    <Layout>
      <div className="flex flex-col h-[calc(100vh-8rem)] max-w-5xl mx-auto bg-gray-900 border border-gray-800 rounded-2xl shadow-xl overflow-hidden">
        {/* Header */}
        <div className="bg-gray-850 px-6 py-4 border-b border-gray-800 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-primary-500/10 text-primary-400 flex items-center justify-center border border-primary-500/20">
              <Bot className="w-5 h-5" />
            </div>
            <div>
              <h2 className="text-lg font-semibold text-white">AI Code Assistant</h2>
              <p className="text-xs text-gray-400">Powered by Spring AI & OpenAI/Gemini</p>
            </div>
          </div>
          <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-medium bg-emerald-500/10 text-emerald-400 border border-emerald-500/20">
            <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span> Online
          </span>
        </div>

        {/* Message List */}
        <div className="flex-1 overflow-y-auto p-6 space-y-6">
          {messages.map((msg, idx) => (
            <div key={idx} className={`flex items-start gap-4 ${msg.role === 'user' ? 'flex-row-reverse' : ''}`}>
              <div className={`w-9 h-9 rounded-xl flex items-center justify-center flex-shrink-0 ${
                msg.role === 'user' 
                  ? 'bg-primary-600 text-white' 
                  : 'bg-gray-800 text-primary-400 border border-gray-700'
              }`}>
                {msg.role === 'user' ? <UserIcon className="w-5 h-5" /> : <Bot className="w-5 h-5" />}
              </div>
              <div className={`max-w-[75%] rounded-2xl px-5 py-3.5 text-sm ${
                msg.role === 'user'
                  ? 'bg-primary-600 text-white rounded-tr-none'
                  : 'bg-gray-800 text-gray-200 border border-gray-700 rounded-tl-none'
              }`}>
                <MarkdownMessage content={msg.content} />
              </div>
            </div>
          ))}
          {loading && (
            <div className="flex items-start gap-4">
              <div className="w-9 h-9 rounded-xl bg-gray-800 text-primary-400 border border-gray-700 flex items-center justify-center">
                <Bot className="w-5 h-5" />
              </div>
              <div className="bg-gray-800 border border-gray-700 rounded-2xl rounded-tl-none px-5 py-3.5 text-sm text-gray-400 flex items-center gap-2">
                <span className="w-2 h-2 bg-primary-400 rounded-full animate-bounce"></span>
                <span className="w-2 h-2 bg-primary-400 rounded-full animate-bounce [animation-delay:0.2s]"></span>
                <span className="w-2 h-2 bg-primary-400 rounded-full animate-bounce [animation-delay:0.4s]"></span>
              </div>
            </div>
          )}
        </div>

        {/* Input Form */}
        <form onSubmit={handleSend} className="p-4 bg-gray-850 border-t border-gray-800 flex gap-3">
          <input
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            placeholder="Ask anything about your code, refactoring, or bugs..."
            className="flex-1 bg-gray-900 border border-gray-700 rounded-xl px-4 py-3 text-sm text-white placeholder-gray-500 focus:outline-none focus:border-primary-500"
          />
          <button
            type="submit"
            disabled={!input.trim() || loading}
            className="bg-primary-600 hover:bg-primary-500 disabled:opacity-50 text-white px-6 py-3 rounded-xl font-medium transition-all flex items-center gap-2 shadow-lg shadow-primary-600/30"
          >
            <Send className="w-4 h-4" /> Send
          </button>
        </form>
      </div>
    </Layout>
  );
}
